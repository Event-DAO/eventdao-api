package com.eventdao.api.entity.customer;

import com.eventdao.api.entity.converter.PasswordEncryptConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "customer_password")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    @JsonIgnore
    @Convert(converter = PasswordEncryptConverter.class)
    private String password;
    private boolean isTemporary;
    private boolean isExpired;
    private LocalDateTime expiry;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
}
