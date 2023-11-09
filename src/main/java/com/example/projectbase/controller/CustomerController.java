package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.CustomerDto;
import com.example.projectbase.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @Operation(summary = "API update customer")
    @PutMapping(UrlConstant.Customer.UPDATE_CUSTOMER)
    public ResponseEntity<?> updateCustomer(@PathVariable int customerId, @Valid @RequestBody CustomerDto customerDto) {
        return VsResponseUtil.success(customerService.updateCustomer(customerId, customerDto));
    }
}
