package com.foneloanMini.repository;

import com.foneloanMini.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    // You can add custom query methods here if needed
}
