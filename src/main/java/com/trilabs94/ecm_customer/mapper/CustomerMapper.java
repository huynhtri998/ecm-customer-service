package com.trilabs94.ecm_customer.mapper;

import com.trilabs94.ecm_customer.dto.AddressDto;
import com.trilabs94.ecm_customer.dto.CustomerDto;
import com.trilabs94.ecm_customer.entity.Customer;

import java.util.List;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer) {
        List<AddressDto> addressDtos = customer.getAddress()
                .stream()
                .map(AddressMapper::addressToAddressDto)
                .toList();

        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstname())
                .lastName(customer.getLastname())
                .email(customer.getEmail())
                .createDate(customer.getCreatedAt())
                .address(addressDtos)
                .build();
    }

    public List<CustomerDto> mapToListCustomerDto(List<Customer> customer) {
        return customer.stream()
                .map(CustomerMapper::mapToCustomerDto)
                .toList();

    }
}
