package com.trilabs94.ecm_customer.service.impl;

import com.trilabs94.ecm_customer.dto.AddressDto;
import com.trilabs94.ecm_customer.dto.CustomerDto;
import com.trilabs94.ecm_customer.entity.Address;
import com.trilabs94.ecm_customer.entity.Customer;
import com.trilabs94.ecm_customer.exception.CustomerAlreadyExistsException;
import com.trilabs94.ecm_customer.exception.ResourceNotFoundException;
import com.trilabs94.ecm_customer.mapper.AddressMapper;
import com.trilabs94.ecm_customer.mapper.CustomerMapper;
import com.trilabs94.ecm_customer.repository.CustomerRepository;
import com.trilabs94.ecm_customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public Page<CustomerDto> getAll(Pageable pageable){
        Page<Long> ids = customerRepository.findIds(pageable);
        List<CustomerDto> dtos = customerRepository.findWithAddressesByIds(ids.getContent())
                                                    .stream()
                                                    .map(CustomerMapper::mapToCustomerDto)
                                                    .toList();
        return new PageImpl<>(dtos, pageable, ids.getTotalElements());
    }

    @Override
    @Transactional
    public CustomerDto getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id: " + customerId)
        );

        return CustomerMapper.mapToCustomerDto(customer);
    }

    @Override
    @Transactional
    public boolean updateCustomer(CustomerDto customerDto) {
        boolean isUpdate = false;
        if (customerDto != null) {
            Customer customer = customerRepository.findById(customerDto.getId()).orElseThrow(
                    () -> new ResourceNotFoundException("Customer not found with id: " + customerDto.getId())
            );

            CustomerMapper.mapToCustomer(customerDto, customer);
            Map<Long, Address> addressMap = customer.getAddress()
                    .stream()
                    .collect(java.util.stream.Collectors.toMap(Address::getId, address -> address));

            for (AddressDto addressValue : customerDto.getAddress()) {
                if(addressValue.getId() != null){
                    Address address = addressMap.get(addressValue.getId());
                    if(address != null){
                        AddressMapper.addressDtoToAddress(addressValue, address);
                    }
                }else {
                    Address newAddress = new Address();
                    AddressMapper.addressDtoToAddress(addressValue, newAddress);
                    newAddress.setCustomer(customer);
                    customer.getAddress().add(newAddress);
                }
            }

            customerRepository.save(customer);
            isUpdate = true;
        }

        return isUpdate;
    }


    @Override
    @Transactional
    public boolean createCustomer(CustomerDto customerDto) {
        if (customerRepository.existsByEmail((customerDto.getEmail()))) {
            throw new CustomerAlreadyExistsException("Customer already exists with email: " + customerDto.getEmail());
        }

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        customer.setCreatedAt(LocalDate.now());
        if (customerDto.getAddress() != null) {
            for (AddressDto addressDto : customerDto.getAddress()) {
                Address address = new Address();
                AddressMapper.addressDtoToAddress(addressDto, address);
                address.setCustomer(customer);
                customer.getAddress().add(address);
            }
        }

        Customer saved = customerRepository.save(customer);
        if (saved.getId() != null) {
            customerDto.setId(saved.getId());
            if (saved.getAddress() != null) {
                for (int i = 0; i < saved.getAddress().size(); i++) {
                    Address address = saved.getAddress().get(i);
                    customerDto.getAddress().get(i).setId(address.getId());
                }
            }
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public boolean deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
        return true;
    }
}
