package com.example.projectbase.service;

import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;

public interface ProductService {

PaginationResponseDto<GetProductsResponseDto> getProducts(PaginationFullRequestDto request);

}
