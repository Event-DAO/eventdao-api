package com.eventdao.api.service.auth;

import com.eventdao.api.dto.request.auth.OTPRequest;
import com.eventdao.api.entity.constant.OTPEnum;
import com.eventdao.api.entity.customer.Customer;
import com.eventdao.api.entity.customer.CustomerDetail;
import com.eventdao.api.entity.customer.CustomerOtpCode;
import com.eventdao.api.exception.*;
import com.eventdao.api.repository.auth.CustomerOtpRepository;
import com.eventdao.api.service.EmailService;
import com.eventdao.api.service.customer.CustomerDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CustomerOtpService {

    private static final int EXPIRY_MINUTE = 5;
    private static final int CODE_LENGTH = 6;
    private final CustomerOtpRepository otpRepository;
    private final CustomerDetailService customerDetailService;
    private final EmailService emailService;

    public static String generateCode() {
        Random random = new Random();
        int code = random.nextInt((int) Math.pow(10, CODE_LENGTH));
        return String.format("%0" + CODE_LENGTH + "d", code);
    }

    public CustomerOtpCode getCustomerOtpCode(Customer customer) throws OtpValidationException {
        Optional<CustomerOtpCode> customerOtpCode = otpRepository.findByCustomerIdAndValidatedAndExpiryAfter(customer.getId(), false, LocalDateTime.now().toInstant(ZoneOffset.UTC));

        if (customerOtpCode.isEmpty()) {
            throw new OtpValidationException();
        }

        return customerOtpCode.get();
    }

    public boolean sendEmailCode(Customer customer) throws EventDaoException {
        CustomerOtpCode customerEmailCode = getCustomerOtpCode(customer);
        //Validations
        if (customerEmailCode != null && LocalDateTime.now(ZoneOffset.UTC).isBefore(customerEmailCode.getExpiry()))
            throw new ExistingEmailCodeException();
        Integer emailCode = 123456; //TODO create it from a random code generator
        CustomerOtpCode otpCode = new CustomerOtpCode();
        otpCode.setCustomerId(customer.getId());
        otpCode.setCode(emailCode);
        otpCode.setExpiry(LocalDateTime.now(ZoneOffset.UTC).plusMinutes(EXPIRY_MINUTE));
        otpCode.setCreated(LocalDateTime.now(ZoneOffset.UTC));
        otpRepository.save(otpCode);
        return true;
    }

    public boolean verifyEmail(Customer customer, String code) throws EventDaoException {
        CustomerOtpCode otpCode = getCustomerOtpCode(customer);
        //Validations
        if (otpCode == null) {
            throw new NoActiveEmailCodeException();
        }
        if (LocalDateTime.now(ZoneOffset.UTC).isAfter(otpCode.getExpiry())) throw new NoActiveEmailCodeException();
        if (!otpCode.getCode().equals(code)) throw new OtpValidationException();
        otpCode.setValidated(true);
        otpRepository.save(otpCode);
        CustomerDetail detail = customerDetailService.findByCustomerId(customer.getId());
        if (!detail.isEmailValidated()) {
            detail.setEmailValidated(true);
            customerDetailService.save(detail);
        }
        return true;
    }

    public List<OTPEnum> sendCustomerOTPs(Customer customer) throws EventDaoException {
        List<OTPEnum> otpList = new ArrayList<>();
        if (getCustomerOtpCode(customer) != null) {
            otpList.add(OTPEnum.OTP_EMAIL);
            return otpList;
        }
        return otpList;
    }


    public void verifyCustomerOTPCode(Customer customer, OTPRequest request) throws EventDaoException {
        CustomerOtpCode otpCode = getCustomerOtpCode(customer);
        if (request.getEmail() != null
                && request.getOtpCode() != null
                && otpCode.getCode().compareTo(request.getOtpCode()) == 0 ) {
            return;
        }else {
            throw new OTPVerificationFailedException();
        }


    }

}
