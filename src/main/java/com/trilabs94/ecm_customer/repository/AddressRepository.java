package com.trilabs94.ecm_customer.repository;

import com.trilabs94.ecm_customer.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByCustomerId(Long customerId);

    boolean existsByCustomerId(Long customerId);
}
