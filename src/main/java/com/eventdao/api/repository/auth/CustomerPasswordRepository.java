package com.eventdao.api.repository.auth;

import com.eventdao.api.entity.customer.CustomerPassword;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerPasswordRepository extends CrudRepository<CustomerPassword, Long> {

    Optional<CustomerPassword> findByCustomerId(Long customerId);
}
