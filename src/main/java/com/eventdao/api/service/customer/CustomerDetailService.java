package com.eventdao.api.service.customer;

import com.eventdao.api.entity.customer.CustomerDetail;
import com.eventdao.api.repository.customer.CustomerDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetailService {

    private final CustomerDetailRepository customerDetailRepository;

    public CustomerDetail findByCustomerId(long customerId){
        CustomerDetail customerDetail = customerDetailRepository.findByCustomerId(customerId);
        if (customerDetail == null) {
            new UsernameNotFoundException("Customer detail not found with id: " + customerId);
        }

        return customerDetail;
    }

    public CustomerDetail save(CustomerDetail customerDetail) {
        return customerDetailRepository.save(customerDetail);
    }
}
