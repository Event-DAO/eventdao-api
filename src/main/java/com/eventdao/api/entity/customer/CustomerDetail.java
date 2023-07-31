package com.eventdao.api.entity.customer;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "customer_detail")
@Data
@Entity
public class CustomerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Long countryId;
    private boolean otpEnabled;
    private boolean emailValidated;
    private boolean msisdnValidated;
    private boolean identityValidated;
    private boolean identityVerified;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
}
