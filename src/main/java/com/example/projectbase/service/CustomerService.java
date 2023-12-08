package com.example.projectbase.service;

import com.example.projectbase.domain.dto.CustomerDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.entity.Customer;

public interface CustomerService {
    Customer createCustomer(CustomerDto customerDto);

    CommonResponseDto updateCustomer(int id, CustomerDto customerDto);

    PaginationResponseDto<Customer> getCustomers(PaginationFullRequestDto requestDto);

    CommonResponseDto deleteCustomer(int customerId);

    Customer getCustomerByUser(String userId);

    Customer getById(int customerId);

    PaginationResponseDto<GetProductsResponseDto> getFavoriteProducts(int customerId, PaginationFullRequestDto request);

    boolean checkFavoriteProduct(int customerId, int productId);

    CommonResponseDto addFavoriteProduct(int customerId, int productId);

    CommonResponseDto removeFavoriteProduct(int customerId, int productId);
}
