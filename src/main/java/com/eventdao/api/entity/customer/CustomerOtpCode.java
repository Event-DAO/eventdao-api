package com.eventdao.api.entity.customer;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Table(name = "customer_otp_code")
@Data
@Entity
public class CustomerOtpCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Integer code;
    @CreationTimestamp
    private LocalDateTime created;
    private LocalDateTime expiry;
    private boolean validated;
}