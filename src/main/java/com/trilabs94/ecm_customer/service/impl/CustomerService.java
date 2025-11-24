package com.trilabs94.ecm_customer.service.impl;

import com.trilabs94.common_error_handler.exception.ResourceAlreadyExistsException;
import com.trilabs94.common_error_handler.exception.ResourceNotFoundException;
import com.trilabs94.ecm_customer.dto.AddressRequestDto;
import com.trilabs94.ecm_customer.dto.CustomerRequestDto;
import com.trilabs94.ecm_customer.dto.CustomerResponseDto;
import com.trilabs94.ecm_customer.dto.CustomerSummaryDto;
import com.trilabs94.ecm_customer.entity.Address;
import com.trilabs94.ecm_customer.entity.Customer;
import com.trilabs94.ecm_customer.mapper.AddressMapper;
import com.trilabs94.ecm_customer.mapper.CustomerMapper;
import com.trilabs94.ecm_customer.repository.CustomerRepository;
import com.trilabs94.ecm_customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;

    @Override
    @Transactional
    public CustomerResponseDto createCustomer(CustomerRequestDto requestDto) {
        log.info("Creating new customer with email={}", requestDto.getEmail());

        if (customerRepository.existsByEmailIgnoreCase(requestDto.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    "Customer with email '%s' already exists".formatted(requestDto.getEmail())
            );
        }

        Customer customer = customerMapper.toEntity(requestDto);

        replaceAddresses(customer, requestDto.getAddresses());

        Customer saved = customerRepository.save(customer);
        return customerMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public CustomerResponseDto updateCustomer(Long id, CustomerRequestDto requestDto) {
        log.info("Updating customer id={}", id);

        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id %d not found".formatted(id)
                ));

        if (customerRepository.existsByEmailIgnoreCaseAndIdNot(requestDto.getEmail(), id)) {
            throw new ResourceAlreadyExistsException(
                    "Customer with email '%s' already exists".formatted(requestDto.getEmail())
            );
        }

        customerMapper.updateEntity(existing, requestDto);

        replaceAddresses(existing, requestDto.getAddresses());

        Customer saved = customerRepository.save(existing);
        return customerMapper.toResponseDto(saved);
    }

    @Override
    public CustomerResponseDto getCustomerById(Long id) {
        log.info("Fetching customer id={}", id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id %d not found".formatted(id)
                ));

        return customerMapper.toResponseDto(customer);
    }

    @Override
    public Page<CustomerSummaryDto> getCustomers(Pageable pageable) {
        log.info("Fetching customers with pageable={}", pageable);

        Page<Customer> page = customerRepository.findAll(pageable);
        return page.map(customerMapper::toSummaryDto);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        log.info("Deleting customer id={}", id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id %d not found".formatted(id)
                ));

        customerRepository.delete(customer);
    }


    private void replaceAddresses(Customer customer, List<AddressRequestDto> addressDtos) {
        List<Address> currentAddresses = customer.getAddresses();

        if (addressDtos == null || addressDtos.isEmpty()) {
            currentAddresses.clear();
            return;
        }

        Map<Long, Address> existingById = currentAddresses.stream()
                .collect(Collectors.toMap(Address::getId, a -> a));

        List<Address> finalAddresses = new ArrayList<>();

        for (AddressRequestDto dto : addressDtos) {
            if (dto.getId() != null) {
                Address existing = existingById.remove(dto.getId());
                if (existing != null) {
                    addressMapper.updateEntity(existing, dto);
                    finalAddresses.add(existing);
                } else {
                    throw new IllegalArgumentException("Address id=" + dto.getId() + " does not belong to customer");
                }
            } else {
                Address created = addressMapper.toEntity(dto, customer);
                finalAddresses.add(created);
            }
        }

        currentAddresses.clear();
        currentAddresses.addAll(finalAddresses);
    }
}
