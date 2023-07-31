package com.eventdao.api.entity.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "customer_token")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String referenceId;
    private Long customerId;
    private long customerTokenTypeId;
    private String ip;
    private String ua;
    private String fingerprint;
    private boolean twofactor;
    private byte[] signingKey;
    private String token;
    @CreationTimestamp
    private LocalDateTime created;
    private LocalDateTime expiry;
    public boolean revoked;
    public boolean expired;
}
