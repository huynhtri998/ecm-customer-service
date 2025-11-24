package com.trilabs94.ecm_customer.mapper;

import com.trilabs94.ecm_customer.dto.AddressResponseDto;
import com.trilabs94.ecm_customer.dto.CustomerRequestDto;
import com.trilabs94.ecm_customer.dto.CustomerResponseDto;
import com.trilabs94.ecm_customer.dto.CustomerSummaryDto;
import com.trilabs94.ecm_customer.entity.Address;
import com.trilabs94.ecm_customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    private final AddressMapper addressMapper;

    public Customer toEntity(CustomerRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return Customer.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .build();
    }

    public void updateEntity(Customer customer, CustomerRequestDto dto) {
        if (customer == null || dto == null) {
            return;
        }

        customer.setFirstname(dto.getFirstname());
        customer.setLastname(dto.getLastname());
        customer.setEmail(dto.getEmail());
    }

    public CustomerResponseDto toResponseDto(Customer entity) {
        if (entity == null) {
            return null;
        }

        List<AddressResponseDto> addressDtos = null;
        List<Address> addresses = entity.getAddresses();

        if (addresses != null) {
            addressDtos = addresses.stream()
                    .map(addressMapper::toResponseDto)
                    .toList();
        }

        return CustomerResponseDto.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .addresses(addressDtos)
                .build();
    }

    public CustomerSummaryDto toSummaryDto(Customer entity) {
        if (entity == null) {
            return null;
        }

        return CustomerSummaryDto.builder()
                .id(entity.getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .build();
    }
}