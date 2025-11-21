package com.trilabs94.ecm_customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "Customer",
        description = "Schema to hold Customer information"
)
public class CustomerDto {

    @Positive
    @NotEmpty(message = "Id can not be a null or empty")
    @Schema(
            description = "Unique identifier of the customer", example = "1"
    )
    private Long id;

    @Pattern(regexp = "(^$|[A-Za-z]+)", message = "First name must contain only alphabets")
    @Size(min = 2, max = 30, message = "The length of the first name should be between 2 and 30")
    @NotEmpty(message = "First name can not be a null or empty")
    @Schema(
            description = "First name of the customer", example = "John"
    )
    private String firstName;

    @Pattern(regexp = "(^$|[A-Za-z]+)", message = "Last name must contain only alphabets")
    @Size(min = 2, max = 30, message = "The length of the last name should be between 2 and 30")
    @NotEmpty(message = "Last name can not be a null or empty")
    @Schema(
            description = "Last name of the customer", example = "Doe"
    )
    private String lastName;

    @Email(message = "Email address should be a valid value")
    @NotEmpty(message = "Email address can not be a null or empty")
    @Schema(
            description = "Email address of the customer", example = "John.Doe@gmail.com"
    )
    private String email;

    @Schema(
            description = "Date when the customer was created", example = "2023-10-15"
    )
    private LocalDate createDate;

    @Schema(
            description = "List of addresses associated with the customer"
    )
    private List<AddressDto> address;
}
