package com.eventdao.api.entity.customer;


import com.eventdao.api.entity.constant.CustomerBalanceType;
import com.eventdao.api.entity.converter.CustomerBalanceTypeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "customer_balance")
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Long assetId;
    private BigDecimal quantity;
    @Convert(converter = CustomerBalanceTypeConverter.class)
    @Column(name = "balance_type_id")
    private CustomerBalanceType balanceType;
    private boolean spendable;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
}
