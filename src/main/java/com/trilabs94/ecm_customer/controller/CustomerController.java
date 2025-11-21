package com.trilabs94.ecm_customer.controller;

import com.trilabs94.ecm_customer.dto.CustomerDto;
import com.trilabs94.ecm_customer.dto.ErrorResponseDto;
import com.trilabs94.ecm_customer.dto.PageResponse;
import com.trilabs94.ecm_customer.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Customers",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE customer details"
)
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class CustomerController {
    private final ICustomerService customerService;


    @Operation(
            summary = "Get All Customers REST API",
            description = "REST API to get all customers with pagination"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of customers"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/users")
    public ResponseEntity<PageResponse<CustomerDto>> getAll(Pageable pageable){
        Page<CustomerDto> customers = customerService.getAll(pageable);
        return ResponseEntity.ok().body(PageResponse.of(customers));
    }

    @Operation(
            summary = "Get Customer By ID REST API",
            description = "REST API to get customer by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved customer by ID"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable(name = "id") @Positive Long customerId){
        CustomerDto customerDto = customerService.getCustomerById(customerId);
        return ResponseEntity.ok().body(customerDto);
    }

    @Operation(
            summary = "Search Customer By Email REST API",
            description = "REST API to search customer by email"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved customer by email"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/users/search")
    public ResponseEntity<CustomerDto> searchCustomerByEmail(@Valid @RequestBody CustomerDto customerDto){
        customerDto = customerService.getCustomerByEmail(customerDto.getEmail());
        if (customerDto != null) {
            return ResponseEntity.ok().body(customerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Update Customer REST API",
            description = "REST API to update existing customer details"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updated customer details"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/users")
    public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = customerService.updateCustomer(customerDto);
        if(isUpdated){
            return ResponseEntity.ok().body(customerDto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Create Customer REST API",
            description = "REST API to create a new customer"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully created new customer"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/users")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = customerService.createCustomer(customerDto);
        if(isUpdated){
            return ResponseEntity.ok().body(customerDto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Delete Customer REST API",
            description = "REST API to delete a customer by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Successfully deleted customer"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") @Positive Long id) {
        boolean isDeleted = customerService.deleteCustomer(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
