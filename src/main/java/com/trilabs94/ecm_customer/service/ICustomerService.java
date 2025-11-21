package com.trilabs94.ecm_customer.service;

import com.trilabs94.ecm_customer.dto.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {
    Page<CustomerDto> getAll(Pageable pageable);
    CustomerDto getCustomerById(Long customerId);
    boolean updateCustomer(CustomerDto customerDto);
    boolean createCustomer(CustomerDto customerDto);
    boolean deleteCustomer(Long id);
    CustomerDto getCustomerByEmail(String email);
}
