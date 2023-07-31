package com.eventdao.api.service.auth;

import com.eventdao.api.entity.customer.CustomerToken;
import com.eventdao.api.repository.auth.CustomerTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerTokenService {

    private final CustomerTokenRepository customerTokenRepository;

    public CustomerToken save(CustomerToken customerToken) {
        return customerTokenRepository.save(customerToken);
    }

    public List<CustomerToken> findAllValidTokenByCustomer(long customerId) {
        return customerTokenRepository.findAllValidTokenByCustomer(customerId);
    }

    public List<CustomerToken> saveAll(List<CustomerToken> validUserTokens) {
        return (List<CustomerToken>) customerTokenRepository.saveAll(validUserTokens);
    }
}
