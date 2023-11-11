package com.example.projectbase.repository;

import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("SELECT new com.example.projectbase.domain.dto.response.GetProductsResponseDto(p.productID,p.name,p.image,p.price,p.discount,p.quantity) FROM Product p")
    Page<GetProductsResponseDto> getProducts(Pageable pageable);

    @Query("SELECT new com.example.projectbase.domain.dto.response.GetProductsResponseDto(p.productID,p.name,p.image,p.price,p.discount,p.quantity) FROM Product p WHERE p.category.id=?1")
    Page<GetProductsResponseDto> getProductsByCategoryId(int categoryId,Pageable pageable);
}
