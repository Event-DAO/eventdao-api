package com.eventdao.api.controller;

import com.eventdao.api.dto.GenericResponse;
import com.eventdao.api.dto.request.customer.CustomerRequest;
import com.eventdao.api.dto.request.customer.CustomerWalletUpdateRequest;
import com.eventdao.api.dto.response.customer.CustomerResponse;
import com.eventdao.api.entity.customer.Customer;
import com.eventdao.api.exception.EventDaoException;
import com.eventdao.api.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerContoller {

    private final CustomerService customerService;

    @GetMapping("/detail")
    public ResponseEntity<CustomerResponse> getCustomerDetail(Customer customer) throws EventDaoException {
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(customer, customerResponse);
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping("/update")
    public ResponseEntity<CustomerResponse> saveCustomerDetail(@Valid @RequestBody CustomerRequest customerRequest) throws EventDaoException {
        return ResponseEntity.ok(customerService.save(customerRequest));
    }

    @PostMapping("/save-wallet")
    public ResponseEntity<GenericResponse> saveWalletAddress(@Valid @RequestBody CustomerWalletUpdateRequest customerWalletUpdateRequest, Customer customer) throws EventDaoException {
        customerService.updateWalletAddress(customerWalletUpdateRequest,customer);
        return ResponseEntity.ok(GenericResponse.GENERIC);
    }

}
