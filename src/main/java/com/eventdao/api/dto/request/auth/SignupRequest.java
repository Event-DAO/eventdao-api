package com.eventdao.api.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    @NotBlank
    private String userName;
    private Boolean kvkkContract;
    private Boolean personalDataContract;
    private Boolean marketingContract;
}
