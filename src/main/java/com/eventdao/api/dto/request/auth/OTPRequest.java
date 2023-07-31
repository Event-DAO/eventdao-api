package com.eventdao.api.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OTPRequest {
    @NotBlank
    private String phoneNumber;
    private String email;
    private Integer otpCode;
}
