package com.example.projectbase.service;

import com.example.projectbase.domain.dto.ProductDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.request.CreateProductRequestDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.dto.response.ProductFromCartResponseDto;
import com.example.projectbase.domain.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {

    PaginationResponseDto<GetProductsResponseDto> getProducts(PaginationFullRequestDto request);

    PaginationResponseDto<Product> getProductsForAdmin(PaginationFullRequestDto request);

    PaginationResponseDto<GetProductsResponseDto> getProductsSortByTotal(PaginationRequestDto request);

    Product getProductDetail(int productId);

    PaginationResponseDto<GetProductsResponseDto> getProductsByCategoryId(int categoryId,PaginationFullRequestDto request);

    PaginationResponseDto<GetProductsResponseDto> findProduct(PaginationFullRequestDto request);

    CommonResponseDto createProduct(CreateProductRequestDto requestDto);

    CommonResponseDto updateProduct(int productId, ProductDto productDto);

    CommonResponseDto deleteProduct(int productId);

    List<ProductFromCartResponseDto> getProductsSameAuthor(int productId);

    CommonResponseDto addImages(int productId,List<MultipartFile> files);

    CommonResponseDto deleteImage(int productId, int imageId);

    CommonResponseDto uploadFeaturedImage(int productId,MultipartFile multipartFile);

    int getQuantityProducts();
}
