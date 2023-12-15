package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.CustomerDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "API update customer")
    @PutMapping(value = UrlConstant.Customer.UPDATE_CUSTOMER, consumes = "multipart/form-data")
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
    public ResponseEntity<?> getCustomerByUser(@PathVariable String userId) {
        return VsResponseUtil.success(customerService.getCustomerByUser(userId));
    }

    @Operation(summary = "API get customer by id")
    @GetMapping(UrlConstant.Customer.GET_CUSTOMER)
    public ResponseEntity<?> getCustomerById(@PathVariable int customerId) {
        return VsResponseUtil.success(customerService.getById(customerId));
    }

    @Operation(summary = "API get favorite products by customer id")
    @GetMapping(UrlConstant.Customer.GET_FAVORITE_PRODUCTS)
    public ResponseEntity<?> getFavoriteProducts(@PathVariable int customerId, @ParameterObject @Valid PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(customerService.getFavoriteProducts(customerId, requestDto));
    }

    @Operation(summary = "API check favorite product in customer")
    @GetMapping(UrlConstant.Customer.CHECK_FAVORITE_PRODUCT)
    public ResponseEntity<?> checkFavoriteProduct(@PathVariable int customerId, @PathVariable int productId) {
        return VsResponseUtil.success(customerService.checkFavoriteProduct(customerId, productId));
    }

    @Operation(summary = "API add favorite products")
    @PostMapping(UrlConstant.Customer.ADD_FAVORITE_PRODUCT)
    public ResponseEntity<?> addFavoriteProduct(@PathVariable int customerId, @PathVariable int productId) {
        return VsResponseUtil.success(customerService.addFavoriteProduct(customerId, productId));
    }

    @Operation(summary = "API delete favorite products")
    @DeleteMapping(UrlConstant.Customer.REMOVE_FAVORITE_PRODUCT)
    public ResponseEntity<?> removeFavoriteProduct(@PathVariable int customerId, @PathVariable int productId) {
        return VsResponseUtil.success(customerService.removeFavoriteProduct(customerId, productId));
    }

    @Operation(summary = "API get count customer")
    @GetMapping(UrlConstant.Customer.GET_COUNT_CUSTOMER)
    public ResponseEntity<?> getCountBill() {
        return VsResponseUtil.success(customerService.countCustomer());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "API upload image")
    @PostMapping(value = UrlConstant.Customer.UPLOAD_IMAGE, consumes = "multipart/form-data")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file) {
        return VsResponseUtil.success(customerService.uploadImage(file));
    }

}
