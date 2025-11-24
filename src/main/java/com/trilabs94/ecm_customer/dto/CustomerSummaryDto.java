package com.trilabs94.ecm_customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerSummaryDto {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;
}
