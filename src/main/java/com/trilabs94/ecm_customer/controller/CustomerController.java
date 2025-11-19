package com.trilabs94.ecm_customer.controller;

import com.trilabs94.ecm_customer.dto.CustomerDto;
import com.trilabs94.ecm_customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping("/users")
    public ResponseEntity<List<CustomerDto>> getAll(){
        List<CustomerDto> customers = customerService.getAll();
        return ResponseEntity.ok().body(customers);
    }
}
