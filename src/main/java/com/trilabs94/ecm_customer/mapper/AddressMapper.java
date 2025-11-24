package com.trilabs94.ecm_customer.mapper;

import com.trilabs94.ecm_customer.dto.AddressRequestDto;
import com.trilabs94.ecm_customer.dto.AddressResponseDto;
import com.trilabs94.ecm_customer.entity.Address;
import com.trilabs94.ecm_customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressRequestDto dto, Customer customer) {
        if (dto == null) {
            return null;
        }

        return Address.builder()
                .street(dto.getStreet())
                .houseNumber(dto.getHouseNumber())
                .zipCode(dto.getZipCode())
                .customer(customer)
                .build();
    }

    public AddressResponseDto toResponseDto(Address entity) {
        if (entity == null) {
            return null;
        }

        return AddressResponseDto.builder()
                .id(entity.getId())
                .street(entity.getStreet())
                .houseNumber(entity.getHouseNumber())
                .zipCode(entity.getZipCode())
                .build();
    }

    public void updateEntity(Address entity, AddressRequestDto dto) {
        if (entity == null || dto == null) {
            return;
        }
        entity.setStreet(dto.getStreet());
        entity.setHouseNumber(dto.getHouseNumber());
        entity.setZipCode(dto.getZipCode());
    }
}
