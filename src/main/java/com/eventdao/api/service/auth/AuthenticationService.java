package com.eventdao.api.service.auth;


import com.eventdao.api.dto.GenericResponse;
import com.eventdao.api.dto.customer.UserDetailDto;
import com.eventdao.api.dto.request.auth.*;
import com.eventdao.api.dto.response.auth.JwtResponse;
import com.eventdao.api.dto.response.auth.OTPResponse;
import com.eventdao.api.dto.response.auth.PasswordResetTokenResponse;
import com.eventdao.api.entity.constant.CustomerIdentityType;
import com.eventdao.api.entity.constant.OTPEnum;
import com.eventdao.api.entity.customer.Customer;
import com.eventdao.api.entity.customer.CustomerDetail;
import com.eventdao.api.entity.customer.CustomerPassword;
import com.eventdao.api.entity.customer.CustomerToken;
import com.eventdao.api.exception.EventDaoException;
import com.eventdao.api.exception.ExistingCustomerException;
import com.eventdao.api.exception.KVKKNotConfirmedException;
import com.eventdao.api.security.JwtUtils;
import com.eventdao.api.service.customer.CustomerDetailService;
import com.eventdao.api.service.customer.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerService customerService;
    private final CustomerPasswordService passwordService;
    private final CustomerDetailService detailService;
    private final CustomerTokenService tokenService;
    private final JwtUtils jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomerOtpService otpService;


    @Transactional
    public GenericResponse register(SignupRequest request) throws EventDaoException {

        // Validations
        if (!request.getKvkkContract()) throw new KVKKNotConfirmedException();
        if (customerService.findByUserNameOpt(request.getUserName()).isPresent()) {
            throw new ExistingCustomerException();
        }

        Customer customer = Customer.builder()
                .email(request.getUserName())
                .identityType(CustomerIdentityType.UNDEFINED)
                .build();
        Customer savedCustomer = customerService.save(customer);

        CustomerPassword customerPassword = CustomerPassword.builder()
                .customerId(savedCustomer.getId())
                .password(request.getPassword())
                .build();
        passwordService.save(customerPassword);

        CustomerDetail detail = new CustomerDetail();
        detail.setCustomerId(savedCustomer.getId());
        detail.setCountryId(1L);
        detail.setOtpEnabled(false);
        detail.setEmailValidated(false);
        detail.setMsisdnValidated(false);
        detail.setIdentityValidated(false);
        detail.setIdentityVerified(false);
        detailService.save(detail);

        //otpService.sendSmsCode(savedCustomer);

        return GenericResponse.GENERIC;
    }

    public JwtResponse authenticate(LoginRequest request) {
        Customer customer = customerService.findByUserName(request.getUserName());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customer.getEmail(),
                        request.getPassword()
                )
        );
        revokeAllCustomerTokens(customer);
        //otpService.verifyCustomerOTPs(customer, request);
        UserDetailDto user = (UserDetailDto) customerService.loadUserByCustomer(customer);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllCustomerTokens(user.getCustomer());
        saveCustomerToken(user.getCustomer(), jwtToken);

        return JwtResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .roles(user.getAuthorities())
                .build();

//        return GenericResponse.GENERIC;
    }

    public JwtResponse verifyOTPCodes(OTPRequest request) {
        UserDetails customer = customerService.loadUserByUsername(request.getPhoneNumber());
        //otpService.verifyCustomerOTPs(customer, request);
        UserDetailDto user =  (UserDetailDto) customer;
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllCustomerTokens(user.getCustomer());
        saveCustomerToken(user.getCustomer(), jwtToken);

        return JwtResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .roles(customer.getAuthorities())
                .build();
    }

    private void saveCustomerToken(Customer customer, String jwtToken) {
        var token = CustomerToken.builder()
                .customerId(customer.getId())
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenService.save(token);
    }

    private void revokeAllCustomerTokens(Customer customer) {
        var validCustomerTokens = tokenService.findAllValidTokenByCustomer(customer.getId());
        if (validCustomerTokens.isEmpty())
            return;
        validCustomerTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenService.saveAll(validCustomerTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String refreshToken = request.getHeader("X-Auth-Refresh-Token");
        if (refreshToken == null) {
            return;
        }

        final String userName = jwtService.extractUsernameFromRefreshToken(refreshToken);
        if (userName != null) {
            UserDetailDto customer = (UserDetailDto) this.customerService.loadUserByUsername(userName);
            if (jwtService.isRefreshTokenValid(refreshToken, customer)) {
                var accessToken = jwtService.generateToken(customer);
                var newRefreshToken = jwtService.generateRefreshToken(customer);
                revokeAllCustomerTokens(customer.getCustomer());
                saveCustomerToken(customer.getCustomer(), accessToken);
                var authResponse = JwtResponse.builder()
                        .token(accessToken)
                        .roles(customer.getAuthorities())
                        .refreshToken(newRefreshToken)
                        .build();
                response.addHeader("Content-Type", "application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public OTPResponse passwordReset(PasswordResetRequest request) {
        List<OTPEnum> otpList = Arrays.asList(OTPEnum.OTP_EMAIL);
        return new OTPResponse(otpList);
    }

    public PasswordResetTokenResponse getPasswordResetToken(OTPRequest request) throws EventDaoException {
        UserDetailDto customer = (UserDetailDto) customerService.loadUserByUsername(request.getPhoneNumber());
        otpService.verifyCustomerOTPCode(customer.getCustomer(), request);
        var passwordResetToken = jwtService.generatePasswordResetToken(customer);
        return PasswordResetTokenResponse.builder().passwordResetToken(passwordResetToken).build();
    }

    public void passwordChange(HttpServletRequest request, HttpServletResponse response, VerifyPasswordResetRequest passwordResetRequest) throws IOException {
        final String passwordResetToken = request.getHeader("X-Auth-Password-Reset-Token");
        if (passwordResetToken == null || passwordResetToken.isEmpty()) {
            return;
        }
        final String customerMsisdn = jwtService.extractUsernameFromPasswordResetSecret(passwordResetToken);
        if (customerMsisdn != null) {
            UserDetailDto customer = (UserDetailDto) this.customerService.loadUserByUsername(customerMsisdn);
            if (jwtService.isPasswordResetTokenValid(passwordResetToken, customer)) {
                CustomerPassword password = customer.getCustomerPassword();
                passwordService.save(password);
                String refreshToken = jwtService.generateRefreshToken(customer);
                String accessToken = jwtService.generateToken(customer);
                revokeAllCustomerTokens(customer.getCustomer());
                saveCustomerToken(customer.getCustomer(), accessToken);
                JwtResponse authResponse = JwtResponse.builder()
                        .token(accessToken)
                        .roles(customer.getAuthorities())
                        .refreshToken(refreshToken)
                        .build();
                response.addHeader("Content-Type", "application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
