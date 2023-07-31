package com.eventdao.api.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@Data
@Builder
public class JwtResponse {
	private String token;
	private String refreshToken;
	private Collection<? extends GrantedAuthority> roles;
}
