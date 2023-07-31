package com.eventdao.api.service.auth;

import com.eventdao.api.entity.customer.CustomerPassword;
import com.eventdao.api.repository.auth.CustomerPasswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerPasswordService {

    private final CustomerPasswordRepository customerPasswordRepository;

    public CustomerPassword findById(long id){
        Optional<CustomerPassword> customerPassword = customerPasswordRepository.findById(id);

        if (customerPassword.isEmpty()) {
            new UsernameNotFoundException("Customer pass not found with id: " + id);
        }

        return customerPassword.get();
    }

    public CustomerPassword findByCustomerId(long id){
        Optional<CustomerPassword> customerPassword = customerPasswordRepository.findByCustomerId(id);

        if (customerPassword.isEmpty()) {
            new UsernameNotFoundException("Customer pass not found with id: " + id);
        }

        return customerPassword.get();

    }

    public CustomerPassword save(CustomerPassword customerPassword) {
        return customerPasswordRepository.save(customerPassword);
    }
}
