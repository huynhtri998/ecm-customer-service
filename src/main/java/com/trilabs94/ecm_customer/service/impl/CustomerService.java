package com.trilabs94.ecm_customer.service.impl;

import com.trilabs94.ecm_customer.dto.CustomerDto;
import com.trilabs94.ecm_customer.entity.Customer;
import com.trilabs94.ecm_customer.mapper.CustomerMapper;
import com.trilabs94.ecm_customer.repository.CustomerRepository;
import com.trilabs94.ecm_customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerDto> getAll(){
        CustomerMapper customerMapper = new CustomerMapper();
        return customerMapper.mapToListCustomerDto(customerRepository.findAll());
    }
}
