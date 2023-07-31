package com.eventdao.api.entity.customer;

import com.eventdao.api.entity.constant.CustomerIdentityType;
import com.eventdao.api.entity.converter.CustomerIdentityTypeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "customer")
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String walletAddress;
    private LocalDate dateOfBirth;
    private String publicAddress;


    @Convert(converter = CustomerIdentityTypeConverter.class)
    @Column(name = "identity_type_id")
    private CustomerIdentityType identityType;
    private String identityPrimaryNumber;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;

}
