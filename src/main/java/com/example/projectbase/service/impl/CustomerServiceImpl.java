package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.domain.dto.CustomerDto;
import com.example.projectbase.domain.entity.Customer;
import com.example.projectbase.domain.entity.User;
import com.example.projectbase.domain.mapper.CustomerMapper;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.repository.CustomerRepository;
import com.example.projectbase.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Customer createCustomer(CustomerDto customerDto) {

        Customer customer = customerMapper.toCustomer(customerDto);

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(int id, CustomerDto customerDto) {
        Optional<Customer> customer = customerRepository.findById((id));
        if (customer.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(id)});
        }

        customerRepository.updateCustomer(id, customerDto.getName(), customerDto.getPhonenumber(), customerDto.getAddress());
        return customerRepository.save(customer.get());
    }
}
