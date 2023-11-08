package com.example.projectbase.service.impl;

import com.example.projectbase.domain.dto.CustomerDto;
import com.example.projectbase.domain.entity.Customer;
import com.example.projectbase.domain.mapper.CustomerMapper;
import com.example.projectbase.repository.CustomerRepository;
import com.example.projectbase.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public Customer createCustomer(CustomerDto customerDto) {

        Customer customer=customerMapper.toCustomer(customerDto);

        return customerRepository.save(customer);
    }
}
