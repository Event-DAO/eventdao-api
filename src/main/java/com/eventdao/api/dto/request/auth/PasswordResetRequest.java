package com.eventdao.api.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordResetRequest {
    @NotBlank
    @NotNull
    private String msisdn;
}
