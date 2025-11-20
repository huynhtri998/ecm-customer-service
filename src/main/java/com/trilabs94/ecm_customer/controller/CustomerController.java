package com.trilabs94.ecm_customer.controller;

import com.trilabs94.ecm_customer.dto.CustomerDto;
import com.trilabs94.ecm_customer.dto.PageResponse;
import com.trilabs94.ecm_customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping("/users")
    public ResponseEntity<PageResponse<CustomerDto>> getAll(Pageable pageable){
        Page<CustomerDto> customers = customerService.getAll(pageable);
        return ResponseEntity.ok().body(PageResponse.of(customers));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable(name = "id") Long customerId){
        CustomerDto customerDto = customerService.getCustomerById(customerId);
        return ResponseEntity.ok().body(customerDto);
    }

    @PutMapping("/users")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto){
        boolean isUpdated = customerService.updateCustomer(customerDto);
        if(isUpdated){
            return ResponseEntity.ok().body(customerDto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){
        boolean isUpdated = customerService.createCustomer(customerDto);
        if(isUpdated){
            return ResponseEntity.ok().body(customerDto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        boolean isDeleted = customerService.deleteCustomer(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
