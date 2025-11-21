package com.trilabs94.ecm_customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "Address",
        description = "Schema to hold Address information"
)
public class AddressDto {

    @Positive(message = "Id must be a positive value")
    @NotEmpty(message = "Id can not be a null or empty")
    @Schema(
            description = "Unique identifier of the Address", example = "1"
    )
    private Long id;

    @NotEmpty(message = "Street can not be a null or empty")
    @Schema(
            description = "Street name of the Address", example = "Main St"
    )
    private String street;

    @NotEmpty(message = "House Number can not be a null or empty")
    @Schema(
            description = "House number of the Address", example = "123"
    )
    private String houseNumber;

    @NotEmpty(message = "Zip code can not be a null or empty")
    @Schema(
            description = "Zip code of the Address", example = "10001"
    )
    private String zipCode;
}
