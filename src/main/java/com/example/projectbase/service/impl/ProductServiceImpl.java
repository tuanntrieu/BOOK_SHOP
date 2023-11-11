package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.SortByDataConstant;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.pagination.PagingMeta;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.entity.Category;
import com.example.projectbase.domain.entity.Product;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.repository.CategoryRepository;
import com.example.projectbase.repository.ProductRepository;
import com.example.projectbase.service.ProductService;
import com.example.projectbase.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getProducts(PaginationFullRequestDto request) {
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.PRODUCT);

        Page<GetProductsResponseDto> page = productRepository.getProducts(pageable);

        PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());

        PagingMeta pagingMeta = new PagingMeta(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize(), request.getSortBy(), request.getIsAscending().toString());
        responseDto.setMeta(pagingMeta);
        return responseDto;

    }

    @Override
    public Product getProductDetail(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));
        ;

        return product;
    }

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getProductsByCategoryId(int categoryID,PaginationFullRequestDto request) {
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(()-> new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID,new String[]{String.valueOf(categoryID)}));
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.CATEGORY);

        Page<GetProductsResponseDto> page = productRepository.getProductsByCategoryId(categoryID,pageable);

        PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());

        PagingMeta pagingMeta = new PagingMeta(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize(), request.getSortBy(), request.getIsAscending().toString());
        responseDto.setMeta(pagingMeta);
        return responseDto;
    }


}
