package com.trilabs94.ecm_customer.controller;

import com.trilabs94.ecm_customer.dto.CustomerDto;
import com.trilabs94.ecm_customer.dto.PageResponse;
import com.trilabs94.ecm_customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<PageResponse<CustomerDto>> getAll(Pageable pageable){
        Page<CustomerDto> customers = customerService.getAll(pageable);
        return ResponseEntity.ok().body(PageResponse.of(customers));
    }
}
