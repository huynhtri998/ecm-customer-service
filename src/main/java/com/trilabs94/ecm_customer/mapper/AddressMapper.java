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

    public static Address addressDtoToAddress(AddressDto addressDto, Address address){
        address.setStreet(addressDto.getStreet());
        address.setHouseNumber(addressDto.getHouseNumber());
        address.setZipCode(addressDto.getZipCode());

        return address;
    }
}
