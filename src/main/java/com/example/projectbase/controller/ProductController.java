package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.ProductDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationRequestDto;
import com.example.projectbase.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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

    @Operation(summary = "API get product sort by total")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_SORT_BY_TOTAL)
    public ResponseEntity<?> getProductSort(@Valid @ParameterObject PaginationRequestDto requestDto){
        return VsResponseUtil.success(productService.getProductsSortByTotal(requestDto));
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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API create product")
    @PostMapping(value=UrlConstant.Product.CREATE_PRODUCT,consumes = "multipart/form-data")
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductDto productDto){
        return VsResponseUtil.success(productService.createProduct(productDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API update product")
    @PutMapping(value = UrlConstant.Product.UPDATE_PRODUCT,consumes = "multipart/form-data")
    public ResponseEntity<?> updateProduct(@PathVariable int productId,@Valid @ModelAttribute ProductDto productDto){
        return VsResponseUtil.success(productService.updateProduct(productId,productDto));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "API delete product")
    @DeleteMapping(UrlConstant.Product.DELETE_PRODUCT)
    public ResponseEntity<?> deleteProduct(@PathVariable int productId){
        return VsResponseUtil.success(productService.deleteProduct(productId));
    }
    @Operation(summary = "API get products same author")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_SAME_AUTHOR)
    public ResponseEntity<?> getProductSameAuthor(@PathVariable int productId){
        return VsResponseUtil.success(productService.getProductsSameAuthor(productId));
    }
}
