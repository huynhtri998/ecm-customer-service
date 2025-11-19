package com.trilabs94.ecm_customer.service;

import com.trilabs94.ecm_customer.dto.CustomerDto;

import java.util.List;

public interface ICustomerService {
    List<CustomerDto> getAll();
}
