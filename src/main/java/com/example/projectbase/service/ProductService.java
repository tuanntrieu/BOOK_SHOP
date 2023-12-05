package com.example.projectbase.service;

import com.example.projectbase.domain.dto.ProductDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.dto.response.ProductFromCartResponseDto;
import com.example.projectbase.domain.entity.Product;

import java.util.List;


public interface ProductService {

    PaginationResponseDto<GetProductsResponseDto> getProducts(PaginationFullRequestDto request);

    PaginationResponseDto<GetProductsResponseDto> getProductsSortByTotal(PaginationRequestDto request);

    Product getProductDetail(int productId);

    PaginationResponseDto<GetProductsResponseDto> getProductsByCategoryId(int categoryId,PaginationFullRequestDto request);

    PaginationResponseDto<GetProductsResponseDto> findProduct(PaginationFullRequestDto request);

    Product createProduct(ProductDto productDto);

    CommonResponseDto updateProduct(int productId, ProductDto productDto);

    CommonResponseDto deleteProduct(int productId);

    List<ProductFromCartResponseDto> getProductsSameAuthor(int productId);
}
