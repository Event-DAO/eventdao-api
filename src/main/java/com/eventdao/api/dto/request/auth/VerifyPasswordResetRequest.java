package com.eventdao.api.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VerifyPasswordResetRequest {
    @NotBlank
    @Size(min = 6, max = 40)
    private String newPassword;
}
