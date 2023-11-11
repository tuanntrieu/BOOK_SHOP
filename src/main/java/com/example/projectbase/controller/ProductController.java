package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "API get all products")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS)
    public ResponseEntity<?> getProducts(@Valid @ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProducts(requestDto));
    }

    @Operation(summary = "API get product detail")
    @GetMapping(UrlConstant.Product.GET_PRODUCT_DETAIL)
    public ResponseEntity<?> getProductDetai(@PathVariable int productId){
        return VsResponseUtil.success(productService.getProductDetail(productId));
    }

    @Operation(summary = "API get products by categoryId")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_BY_CATEGORY_ID)
    public ResponseEntity<?> getProductByCategoryId(@PathVariable int categoryId,@ParameterObject @Valid PaginationFullRequestDto requestDto){
        return VsResponseUtil.success(productService.getProductsByCategoryId(categoryId,requestDto));
    }

    @Operation(summary = "API find product")
    @GetMapping(UrlConstant.Product.FIND_PRODUCT)
    public ResponseEntity<?> findProduct(@ParameterObject PaginationFullRequestDto requestDto){
        return VsResponseUtil.success(productService.findProduct(requestDto));
    }
}
