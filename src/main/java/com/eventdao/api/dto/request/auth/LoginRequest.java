package com.eventdao.api.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
	@NotBlank
	private String userName;
	@NotBlank
	private String password;
}
