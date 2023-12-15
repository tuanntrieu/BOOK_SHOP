package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.SortByDataConstant;
import com.example.projectbase.constant.SuccessMessage;
import com.example.projectbase.domain.dto.ProductDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.pagination.PagingMeta;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.dto.response.ProductFromCartResponseDto;
import com.example.projectbase.domain.entity.Category;
import com.example.projectbase.domain.entity.Product;
import com.example.projectbase.domain.entity.ProductImage;
import com.example.projectbase.domain.mapper.ProductMapper;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.repository.CategoryRepository;
import com.example.projectbase.repository.ProductImageRepository;
import com.example.projectbase.repository.ProductRepository;
import com.example.projectbase.service.ProductService;
import com.example.projectbase.util.PaginationUtil;
import com.example.projectbase.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final UploadFileUtil uploadFileUtil;
    private final ProductImageRepository productImageRepository;


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
    public PaginationResponseDto<Product> getProductsForAdmin(PaginationFullRequestDto request) {
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.PRODUCT);
        Page<Product> page = productRepository.getAllForAdmin(pageable);


        PaginationResponseDto<Product> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());

        PagingMeta pagingMeta = new PagingMeta(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize(), request.getSortBy(), request.getIsAscending().toString());
        responseDto.setMeta(pagingMeta);
        return responseDto;
    }

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getProductsSortByTotal(PaginationRequestDto request) {
        Pageable pageable = PaginationUtil.buildPageable(request);
        Page<GetProductsResponseDto> page = productRepository.getProductsSortByTotal(pageable);


        PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());

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
    public PaginationResponseDto<GetProductsResponseDto> getProductsByCategoryId(int categoryID, PaginationFullRequestDto request) {
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, new String[]{String.valueOf(categoryID)}));
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.CATEGORY);

        Page<GetProductsResponseDto> page = productRepository.getProductsByCategoryId(categoryID, pageable);

        PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());

        PagingMeta pagingMeta = new PagingMeta(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize(), request.getSortBy(), request.getIsAscending().toString());
        responseDto.setMeta(pagingMeta);
        return responseDto;
    }

    @Override
    public PaginationResponseDto<GetProductsResponseDto> findProduct(PaginationFullRequestDto request) {
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.PRODUCT);

        Page<GetProductsResponseDto> page = productRepository.findProduct(request.getKeyword(), pageable);
        PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());

        PagingMeta pagingMeta = new PagingMeta(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize(), request.getSortBy(), request.getIsAscending().toString());
        responseDto.setMeta(pagingMeta);
        return responseDto;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCate_id())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productDto.getCate_id())}));

        Product product = productMapper.toProduct(productDto);

        return productRepository.save(product);
    }

    @Override
    public CommonResponseDto updateProduct(int productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));
        ;
        productRepository.updateProduct(productId, productDto.getName(), productDto.getAuthor(), productDto.getQuantity(), productDto.getPrice(), productDto.getDescription(), productDto.getDiscount(), productDto.getSize());
        return new CommonResponseDto(true, SuccessMessage.UPDATE);
    }

    @Override
    public CommonResponseDto deleteProduct(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));
        ;
        productRepository.delete(product);
        return new CommonResponseDto(true, SuccessMessage.DELETE);
    }

    @Override
    public List<ProductFromCartResponseDto> getProductsSameAuthor(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));
        ;
        return productRepository.getProductSameAuthor(productId, product.getAuthor());
    }

    @Override
    public CommonResponseDto addImages(int productId, List<MultipartFile> files) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));
        ;
        List<ProductImage> images = new ArrayList<>();
        for (MultipartFile file : files) {
            ProductImage productImage = new ProductImage();
            productImage.setUrl(uploadFileUtil.uploadFile(file));
            images.add(productImage);
            productImage.setProduct(product);
        }
        product.setImages(images);
        productRepository.save(product);
        return new CommonResponseDto(true,SuccessMessage.UPDATE);
    }

    @Override
    public CommonResponseDto deleteImage(int productId, int imageId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));
        ;
        productImageRepository.deleteProductImage(productId,imageId);
        return new CommonResponseDto(true,SuccessMessage.DELETE);
    }

    @Override
    public CommonResponseDto uploadFeaturedImage(int productId, MultipartFile multipartFile) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));
        ;
        uploadFileUtil.destroyFileWithUrl(product.getFeaturedImage());
        product.setFeaturedImage(uploadFileUtil.uploadFile(multipartFile));
        productRepository.save(product);
        return new CommonResponseDto(true,SuccessMessage.UPDATE);

    }

    @Override
    public int getQuantityProducts() {
        return productRepository.getQuantityProducts();
    }


}
