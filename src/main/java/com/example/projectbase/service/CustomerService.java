package com.example.projectbase.service;

import com.example.projectbase.domain.dto.CustomerDto;
import com.example.projectbase.domain.entity.Customer;

public interface CustomerService {
    Customer createCustomer(CustomerDto customerDto);
}
