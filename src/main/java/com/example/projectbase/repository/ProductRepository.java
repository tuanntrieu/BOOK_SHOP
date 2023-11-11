package com.example.projectbase.repository;

import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.entity.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT new com.example.projectbase.domain.dto.response.GetProductsResponseDto(p.productID,p.name,p.image,p.price,p.discount,p.quantity) FROM Product p")
    Page<GetProductsResponseDto> getProducts(Pageable pageable);

    @Query("SELECT new com.example.projectbase.domain.dto.response.GetProductsResponseDto(p.productID,p.name,p.image,p.price,p.discount,p.quantity) FROM Product p WHERE p.category.id=?1")
    Page<GetProductsResponseDto> getProductsByCategoryId(int categoryId, Pageable pageable);

    @Query("SELECT new com.example.projectbase.domain.dto.response.GetProductsResponseDto(p.productID,p.name,p.image,p.price,p.discount,p.quantity) FROM Product p WHERE (p.name LIKE %:keyword%) OR (p.category.name LIKE %:keyword%)")
    Page<GetProductsResponseDto> findProduct(@Param("keyword") String keyword, Pageable pageable);

    @Query("UPDATE Product p SET p.name=?2,p.image=?3,p.author=?4,p.quantity=?5,p.price=?6,p.description=?7,p.discount=?8 ,p.size=?9 WHERE p.productID=?1 ")
    void updateProduct(int productId, String name, String image, String author, int quantity, float price, String description, float discount, String size);


}
