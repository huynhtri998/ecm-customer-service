package com.trilabs94.ecm_customer.mapper;

import com.trilabs94.ecm_customer.dto.CustomerDto;
import com.trilabs94.ecm_customer.entity.Customer;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setEmail(customerDto.getEmail());
        customer.setFirstname(customerDto.getFirstName());
        customer.setLastname(customerDto.getLastName());

        return customer;
    }
}
