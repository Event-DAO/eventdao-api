package com.eventdao.api.repository.customer;


import com.eventdao.api.entity.customer.CustomerDetail;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDetailRepository extends CrudRepository<CustomerDetail, Long> {

    CustomerDetail findByCustomerId(Long customerId);
}
