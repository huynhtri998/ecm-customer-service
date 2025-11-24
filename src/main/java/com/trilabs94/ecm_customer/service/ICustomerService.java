package com.trilabs94.ecm_customer.service;

import com.trilabs94.ecm_customer.dto.CustomerRequestDto;
import com.trilabs94.ecm_customer.dto.CustomerResponseDto;
import com.trilabs94.ecm_customer.dto.CustomerSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {
    CustomerResponseDto createCustomer(CustomerRequestDto requestDto);

    CustomerResponseDto updateCustomer(Long id, CustomerRequestDto requestDto);

    CustomerResponseDto getCustomerById(Long id);

    Page<CustomerSummaryDto> getCustomers(Pageable pageable);

    void deleteCustomer(Long id);
}
