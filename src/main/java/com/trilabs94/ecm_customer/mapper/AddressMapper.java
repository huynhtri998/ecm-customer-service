package com.trilabs94.ecm_customer.mapper;

import com.trilabs94.ecm_customer.dto.AddressDto;
import com.trilabs94.ecm_customer.entity.Address;

public class AddressMapper {

    public static AddressDto addressToAddressDto(Address address){
        return AddressDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .zipCode(address.getZipCode())
                .build();
    }
}
