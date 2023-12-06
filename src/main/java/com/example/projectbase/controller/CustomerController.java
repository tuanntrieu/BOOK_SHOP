package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.CustomerDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "API update customer")
    @PutMapping(value=UrlConstant.Customer.UPDATE_CUSTOMER,consumes = "multipart/form-data")
    public ResponseEntity<?> updateCustomer(@PathVariable int customerId, @Valid @ModelAttribute CustomerDto customerDto) {
        return VsResponseUtil.success(customerService.updateCustomer(customerId, customerDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API get customers")
    @GetMapping(UrlConstant.Customer.GET_CUSTOMERS)
    public ResponseEntity<?> getCustomers(@Valid @ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(customerService.getCustomers(requestDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "API delete customer")
    @DeleteMapping(UrlConstant.Customer.DELETE_CUSTOMER)
    public ResponseEntity<?> deleteCustomer(@PathVariable int customerId) {
        return VsResponseUtil.success(customerService.deleteCustomer(customerId));
    }

    @Operation(summary = "API get customer by userId")
    @GetMapping(UrlConstant.Customer.GET_CUSTOMER_BY_USER)
    public ResponseEntity<?> getCustomerByUser(@PathVariable String userId){
        return VsResponseUtil.success(customerService.getCustomerByUser(userId));
    }

    @Operation(summary = "API get customer by id")
    @GetMapping(UrlConstant.Customer.GET_CUSTOMER)
    public ResponseEntity<?> getCustomerById(@PathVariable int customerId){
        return VsResponseUtil.success(customerService.getById(customerId));
    }
}
