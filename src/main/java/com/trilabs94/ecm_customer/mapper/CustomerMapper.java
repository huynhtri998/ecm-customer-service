package com.trilabs94.ecm_customer.mapper;

import com.trilabs94.ecm_customer.dto.AddressDto;
import com.trilabs94.ecm_customer.dto.CustomerDto;
import com.trilabs94.ecm_customer.entity.Customer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstname())
                .lastName(customer.getLastname())
                .email(customer.getEmail())
                .createDate(customer.getCreatedAt())
                .address(
                        Optional.ofNullable(customer.getAddress())
                                .orElseGet(Collections::emptyList)
                                .stream()
                                .map(AddressMapper::addressToAddressDto)
                                .toList()
                )
                .build();
    }

    public static List<CustomerDto> mapToListCustomerDto(List<Customer> customer) {
        return customer.stream()
                .map(CustomerMapper::mapToCustomerDto)
                .toList();

    }
}
