package com.trilabs94.ecm_customer.repository;

import com.trilabs94.ecm_customer.dto.CustomerDto;
import com.trilabs94.ecm_customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("select c.id from Customer c")
    Page<Long> findIds(Pageable pageable);

    @Query("""
        select distinct c
            from Customer c
            left join fetch c.address
            where c.id in :ids
    """)
    List<Customer> findWithAddressesByIds(@Param("ids") List<Long> ids);

    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);
}
