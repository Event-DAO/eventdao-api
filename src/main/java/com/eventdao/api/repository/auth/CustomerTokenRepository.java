package com.eventdao.api.repository.auth;

import com.eventdao.api.entity.customer.CustomerToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerTokenRepository extends CrudRepository<CustomerToken, Long> {

    CustomerToken findByCustomerId(long customerId);

    CustomerToken findByToken(String token);

//    @Query(value = "SELECT t FROM CustomerToken t WHERE t.customerId = :customerId AND (t.expired = FALSE OR t.revoked = FALSE)")
//    List<CustomerToken> findAllValidTokenByCustomer(long customerId);

    @Query(value = "SELECT * FROM customer_token t WHERE t.customer_id = :customerId AND (t.expired = FALSE OR t.revoked = FALSE)",nativeQuery = true)
    List<CustomerToken> findAllValidTokenByCustomer(long customerId);
}
