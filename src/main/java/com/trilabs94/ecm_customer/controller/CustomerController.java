package com.trilabs94.ecm_customer.controller;

import com.trilabs94.ecm_customer.dto.CustomerRequestDto;
import com.trilabs94.ecm_customer.dto.CustomerResponseDto;
import com.trilabs94.ecm_customer.dto.CustomerSummaryDto;
import com.trilabs94.ecm_customer.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Validated
@Schema(
        name = "Customer Controller",
        description = "REST APIs for managing customers in the Ecommerce Customer Management System"
)
public class CustomerController {

    private final ICustomerService customerService;

    @Operation(
            summary = "Create a new customer",
            description = "Create a new customer with basic information and optional addresses."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Customer created",
                    content = @Content(schema = @Schema(implementation = CustomerResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Customer with given email already exists",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(
            @Valid @RequestBody CustomerRequestDto requestDto
    ) {
        CustomerResponseDto created = customerService.createCustomer(requestDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @Operation(
            summary = "Update an existing customer",
            description = "Update customer information and replace its addresses with the given list."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer updated",
                    content = @Content(schema = @Schema(implementation = CustomerResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Customer with given email already exists",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequestDto requestDto
    ) {
        CustomerResponseDto updated = customerService.updateCustomer(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Get customer by ID",
            description = "Retrieve full customer details by ID, including addresses."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer found",
                    content = @Content(schema = @Schema(implementation = CustomerResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
        CustomerResponseDto dto = customerService.getCustomerById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Get customers (paged)",
            description = "Retrieve a paginated list of customers (summary view)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Customers page",
                    content = @Content(schema = @Schema(implementation = CustomerSummaryDto.class))
            )
    })
    @GetMapping
    public ResponseEntity<Page<CustomerSummaryDto>> getCustomers(Pageable pageable) {
        Page<CustomerSummaryDto> page = customerService.getCustomers(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Delete customer",
            description = "Delete an existing customer by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Customer deleted",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
