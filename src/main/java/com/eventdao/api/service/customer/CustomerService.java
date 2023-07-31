package com.eventdao.api.service.customer;

import com.eventdao.api.dto.customer.UserDetailDto;
import com.eventdao.api.dto.request.customer.CustomerRequest;
import com.eventdao.api.dto.request.customer.CustomerWalletUpdateRequest;
import com.eventdao.api.dto.response.customer.CustomerResponse;
import com.eventdao.api.entity.constant.Role;
import com.eventdao.api.entity.customer.Customer;
import com.eventdao.api.entity.customer.CustomerPassword;
import com.eventdao.api.entity.customer.CustomerToken;
import com.eventdao.api.exception.CustomerUpdateException;
import com.eventdao.api.repository.auth.CustomerPasswordRepository;
import com.eventdao.api.repository.auth.CustomerTokenRepository;
import com.eventdao.api.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final CustomerPasswordRepository customerPasswordRepository;
    private final CustomerTokenRepository customerTokenRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Customer customer = findByUserName(userName);

        Optional<CustomerPassword> customerPassword = customerPasswordRepository.findByCustomerId(customer.getId());
        return UserDetailDto.builder().customer(customer).roles(Arrays.asList(Role.ROLE_USER_BASIC)).customerPassword(customerPassword.get()).build();
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        Customer customer = findById(id);

        Optional<CustomerPassword> customerPassword = customerPasswordRepository.findByCustomerId(customer.getId());
        return UserDetailDto.builder().customer(customer).roles(Arrays.asList(Role.ROLE_USER_BASIC)).customerPassword(customerPassword.get()).build();
    }

    public UserDetails loadUserByCustomer(Customer customer) throws UsernameNotFoundException {
        if (customer == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Optional<CustomerPassword> customerPassword = customerPasswordRepository.findByCustomerId(customer.getId());
        return UserDetailDto.builder().customer(customer).roles(Arrays.asList(Role.ROLE_USER_BASIC)).customerPassword(customerPassword.get()).build();
    }

    public CustomerToken getCustomerToken(long customerId){
        CustomerToken customerToken = customerTokenRepository.findByCustomerId(customerId);
        if (customerToken == null) {
            throw new UsernameNotFoundException("Customer token not found with id: " + customerId);
        }
        return customerToken;
    }

    public Customer findById(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            throw new UsernameNotFoundException("Customer not found with id: " + customerId);
        }

        return customer.get();
    }

    public Customer findByUserName(String userName) {
        Optional<Customer> customerOpt = customerRepository.findByEmailOrPhoneOrWalletAddress(userName, userName, userName);
        if (customerOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + userName);
        }
        return customerOpt.get();
    }

    public Optional<Customer> findByUserNameOpt(String userName) {
        Optional<Customer> customerOpt = customerRepository.findByEmailOrPhoneOrWalletAddress(userName, userName, userName);
        return customerOpt;
    }

    public CustomerResponse getCustomerResponseByUserName(String username) {
        Customer customer = findByUserName(username);
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(customer, customerResponse);
        return customerResponse;
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public CustomerResponse save(CustomerRequest customerRequest) {
        Optional<Customer> customerOpt = customerRepository.findByEmailOrPhoneOrWalletAddress(customerRequest.getEmail(), customerRequest.getPhone(), customerRequest.getWalletAddress());

        Customer customer = new Customer();
        if (customerOpt.isEmpty()) {
            //BeanUtils.copyProperties(customerRequest,customer);
            throw new UsernameNotFoundException("Username not found");
        } else {
            customer.setDateOfBirth(customerRequest.getDateOfBirth());
            customer.setFirstName(customerRequest.getFirstName());
            customer.setMiddleName(customerRequest.getMiddleName());
            customer.setLastName(customerRequest.getLastName());
            customer.setIdentityType(customerRequest.getIdentityType());
            customer.setIdentityPrimaryNumber(customerRequest.getIdentityPrimaryNumber());
            customer.setPublicAddress(customerRequest.getPublicAddress());
        }

        Customer updatedCustomer = save(customer);
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(updatedCustomer, customerResponse);

        return customerResponse;
    }

    public void updateWalletAddress(CustomerWalletUpdateRequest customerWalletUpdateRequest, Customer customer) throws CustomerUpdateException {
        customer.setWalletAddress(customerWalletUpdateRequest.getWalletAddress());
        Customer updateCustomer = customerRepository.save(customer);
        if (!StringUtils.hasText(updateCustomer.getWalletAddress())) {
            throw new CustomerUpdateException();
        }
    }
}
