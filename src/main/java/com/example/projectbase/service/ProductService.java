package com.example.projectbase.service;

import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.entity.Product;

import java.util.Optional;

public interface ProductService {

    PaginationResponseDto<GetProductsResponseDto> getProducts(PaginationFullRequestDto request);

    Product getProductDetail(int productId);

    PaginationResponseDto<GetProductsResponseDto> getProductsByCategoryId(int categoryId,PaginationFullRequestDto request);
}
