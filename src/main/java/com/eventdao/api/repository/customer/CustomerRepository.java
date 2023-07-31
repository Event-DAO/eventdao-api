package com.eventdao.api.repository.customer;

import com.eventdao.api.entity.customer.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByEmailOrPhoneOrWalletAddress(String email, String phone, String walletAddress);
}
