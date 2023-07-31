package com.eventdao.api.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FullOTPRequest {
    @NotBlank
    private String msisdnCode;
    private Integer otpCode;
    private String emailCode;
}
