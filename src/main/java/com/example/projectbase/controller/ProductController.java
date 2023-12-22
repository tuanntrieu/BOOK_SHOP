package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.ProductDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationRequestDto;
import com.example.projectbase.domain.dto.request.CreateProductRequestDto;
import com.example.projectbase.domain.dto.request.FindProductsAdminRequestDto;
import com.example.projectbase.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestApiV1
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "API get all products")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS)
    public ResponseEntity<?> getProducts(@Valid @ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProducts(requestDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API get all products for admin")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_ADMIN)
    public ResponseEntity<?> getProductsForAdmin(@Valid @ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProductsForAdmin(requestDto));
    }

    @Operation(summary = "API get product sort by total")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_SORT_BY_TOTAL)
    public ResponseEntity<?> getProductSort(@Valid @ParameterObject PaginationRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProductsSortByTotal(requestDto));
    }

    @Operation(summary = "API get product detail")
    @GetMapping(UrlConstant.Product.GET_PRODUCT_DETAIL)
    public ResponseEntity<?> getProductDetai(@PathVariable int productId) {
        return VsResponseUtil.success(productService.getProductDetail(productId));
    }

    @Operation(summary = "API get products by categoryId")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_BY_CATEGORY_ID)
    public ResponseEntity<?> getProductByCategoryId(@PathVariable int categoryId, @ParameterObject @Valid PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProductsByCategoryId(categoryId, requestDto));
    }

    @Operation(summary = "API find product")
    @GetMapping(UrlConstant.Product.FIND_PRODUCT)
    public ResponseEntity<?> findProduct(@ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.findProduct(requestDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API create product")
    @PostMapping(value = UrlConstant.Product.CREATE_PRODUCT)
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductRequestDto requestDto) {
        return VsResponseUtil.success(productService.createProduct(requestDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API update product")
    @PutMapping(value = UrlConstant.Product.UPDATE_PRODUCT, consumes = "multipart/form-data")
    public ResponseEntity<?> updateProduct(@PathVariable int productId, @Valid @ModelAttribute ProductDto productDto) {
        return VsResponseUtil.success(productService.updateProduct(productId, productDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API delete product")
    @DeleteMapping(UrlConstant.Product.DELETE_PRODUCT)
    public ResponseEntity<?> deleteProduct(@PathVariable int productId) {
        return VsResponseUtil.success(productService.deleteProduct(productId));
    }

    @Operation(summary = "API get products same author")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_SAME_AUTHOR)
    public ResponseEntity<?> getProductSameAuthor(@PathVariable int productId) {
        return VsResponseUtil.success(productService.getProductsSameAuthor(productId));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API add images")
    @PatchMapping(value = UrlConstant.Product.ADD_IMAGE_FOR_PRODUCT, consumes = "multipart/form-data")
    public ResponseEntity<?> addImage(@PathVariable int productId, @RequestParam List<MultipartFile> files) {
        return VsResponseUtil.success(productService.addImages(productId, files));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API upload featured image")
    @PatchMapping(value = UrlConstant.Product.UPLOAD_FEATURED_IMAGE, consumes = "multipart/form-data")
    public ResponseEntity<?> uploadFeaturedImage(@PathVariable int productId, @RequestParam MultipartFile file) {
        return VsResponseUtil.success(productService.uploadFeaturedImage(productId, file));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API delete images")
    @DeleteMapping(UrlConstant.Product.DELETE_PRODUCT_IMAGE)
    public ResponseEntity<?> deleteImage(@PathVariable int productId, @PathVariable int imageId) {
        return VsResponseUtil.success(productService.deleteImage(productId, imageId));
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @Operation(summary = "API get quantity products")
    @GetMapping(UrlConstant.Product.GET_QUANTITY_PRODUCTS)
    public ResponseEntity<?> getRevenue() {
        return VsResponseUtil.success(productService.getQuantityProducts());
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @Operation(summary = "API find products admin")
    @GetMapping(UrlConstant.Product.FIND_PRODUCTS_ADMIN)
    public ResponseEntity<?> findProductsAdmin(@ParameterObject PaginationFullRequestDto requestDto, @RequestBody FindProductsAdminRequestDto request) {
        return VsResponseUtil.success(productService.findProductsAdmin(requestDto, request));
    }

}
