package com.eventdao.api.dto.response.auth;

import com.eventdao.api.entity.customer.Customer;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class CustomerDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String msisdn;
    private LocalDate dateOfBirth;
    private String identityPrimaryNumber;
    private String identitySecondaryNumber;
    private Collection<? extends GrantedAuthority> roles;

    public CustomerDTO(Customer customer){
        this.firstName = customer.getFirstName();
        this.middleName = customer.getMiddleName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.msisdn = customer.getPhone();
        this.dateOfBirth = customer.getDateOfBirth();
        this.identityPrimaryNumber = customer.getIdentityPrimaryNumber();
    }
}
