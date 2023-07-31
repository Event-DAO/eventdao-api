package com.eventdao.api.dto.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordResetTokenResponse {
    private String passwordResetToken;
}
