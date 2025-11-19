package com.trilabs94.ecm_customer.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    private Long id;
    private String street;
    private String houseNumber;
    private String zipCode;
}
