package com.trilabs94.ecm_customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDto {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private OffsetDateTime createdAt;

    private List<AddressResponseDto> addresses;
}
