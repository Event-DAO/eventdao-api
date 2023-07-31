package com.eventdao.api.repository.auth;

import com.eventdao.api.entity.customer.CustomerOtpCode;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.Optional;

public interface CustomerOtpRepository extends CrudRepository<CustomerOtpCode, Long> {

    Optional<CustomerOtpCode> findByCustomerIdAndValidatedAndExpiryAfter(long customerId, boolean validate, Instant now);
}