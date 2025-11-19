package com.trilabs94.ecm_customer.service.impl;

import com.trilabs94.ecm_customer.dto.CustomerDto;
import com.trilabs94.ecm_customer.entity.Customer;
import com.trilabs94.ecm_customer.mapper.CustomerMapper;
import com.trilabs94.ecm_customer.repository.CustomerRepository;
import com.trilabs94.ecm_customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Page<CustomerDto> getAll(Pageable pageable){
        Page<Long> ids = customerRepository.findIds(pageable);
        List<CustomerDto> dtos = customerRepository.findWithAddressesByIds(ids.getContent())
                                                    .stream()
                                                    .map(CustomerMapper::mapToCustomerDto)
                                                    .toList();
        return new PageImpl<>(dtos, pageable, ids.getTotalElements());
    }
}
