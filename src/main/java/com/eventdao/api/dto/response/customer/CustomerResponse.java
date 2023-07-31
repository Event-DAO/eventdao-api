package com.eventdao.api.dto.response.customer;

import com.eventdao.api.entity.constant.CustomerIdentityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String walletAddress;
    private LocalDate dateOfBirth;
    private String publicAddress;
    private CustomerIdentityType identityType;
    private String identityPrimaryNumber;
    private LocalDateTime created;
}
