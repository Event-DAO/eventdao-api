package com.eventdao.api.controller;


import com.eventdao.api.dto.GenericResponse;
import com.eventdao.api.dto.request.auth.*;
import com.eventdao.api.dto.response.auth.JwtResponse;
import com.eventdao.api.dto.response.auth.OTPResponse;
import com.eventdao.api.dto.response.auth.PasswordResetTokenResponse;
import com.eventdao.api.exception.EventDaoException;
import com.eventdao.api.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<GenericResponse> signUp(@Valid @RequestBody SignupRequest signUpRequest) throws EventDaoException {
        return ResponseEntity.ok(authService.register(signUpRequest));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }

    @PostMapping("/password-reset")
    public ResponseEntity<OTPResponse> passwordReset(@Valid @RequestBody PasswordResetRequest request){
        return ResponseEntity.ok(authService.passwordReset(request));
    }

    @PostMapping("/password-change")
    public void passwordChange(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody VerifyPasswordResetRequest passwordResetRequest
    ) throws IOException {
        authService.passwordChange(request, response, passwordResetRequest);
    }

    @PostMapping("/verify-password-otp")
    public ResponseEntity<PasswordResetTokenResponse> passwordChange(@Valid @RequestBody OTPRequest request) throws EventDaoException {
        return ResponseEntity.ok(authService.getPasswordResetToken(request));
    }

    @PostMapping("/verify-otp-codes")
    public ResponseEntity<JwtResponse> verifyOTPCodes(@Valid @RequestBody OTPRequest request) {
        return ResponseEntity.ok(authService.verifyOTPCodes(request));
    }
}
