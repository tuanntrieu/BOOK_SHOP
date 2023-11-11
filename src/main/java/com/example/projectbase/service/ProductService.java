package com.example.projectbase.service;

import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.entity.Product;


public interface ProductService {

    PaginationResponseDto<GetProductsResponseDto> getProducts(PaginationFullRequestDto request);

    Product getProductDetail(int productId);

    PaginationResponseDto<GetProductsResponseDto> getProductsByCategoryId(int categoryId,PaginationFullRequestDto request);

    PaginationResponseDto<GetProductsResponseDto> findProduct(PaginationFullRequestDto request);
}
