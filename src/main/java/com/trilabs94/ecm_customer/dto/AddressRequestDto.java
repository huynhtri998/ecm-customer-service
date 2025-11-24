package com.trilabs94.ecm_customer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequestDto {
    private Long id;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "House number is required")
    private String houseNumber;

    @NotBlank(message = "Zip code is required")
    private String zipCode;
}
