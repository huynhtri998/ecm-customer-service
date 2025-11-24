package com.trilabs94.ecm_customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDto {

    private Long id;

    private String street;

    private String houseNumber;

    private String zipCode;
}
