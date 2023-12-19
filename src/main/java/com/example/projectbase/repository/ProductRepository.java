package com.example.projectbase.repository;

import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.dto.response.ProductFromCartResponseDto;
import com.example.projectbase.domain.entity.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByProductId(int productId);

    @Query("SELECT new com.example.projectbase.domain.dto.response.GetProductsResponseDto(p.productId,p.name,p.featuredImage,p.price,p.discount,p.quantity) FROM Product p")
    Page<GetProductsResponseDto> getProducts(Pageable pageable);

    @Query("SELECT p FROM Product p")
    Page<Product> getAllForAdmin(Pageable pageable);

    @Query("SELECT new com.example.projectbase.domain.dto.response.GetProductsResponseDto(p.productId,p.name,p.featuredImage,p.price,p.discount,p.quantity) FROM Product p WHERE p.category.id=?1")
    Page<GetProductsResponseDto> getProductsByCategoryId(int categoryId, Pageable pageable);

    @Query("SELECT new com.example.projectbase.domain.dto.response.GetProductsResponseDto(p.productId,p.name,p.featuredImage,p.price,p.discount,p.quantity) FROM Product p WHERE (p.name LIKE %:keyword%) OR (p.category.name LIKE %:keyword%)")
    Page<GetProductsResponseDto> findProduct(@Param("keyword") String keyword, Pageable pageable);

    @Query("UPDATE Product p SET p.name=?2,p.author=?3,p.quantity=?4,p.price=?5,p.description=?6,p.discount=?7 ,p.size=?8,p.lastModifiedDate=CURRENT_TIMESTAMP WHERE p.productId=?1 ")
    void updateProduct(int productId, String name, String author, int quantity, int price, String description, float discount, String size);

    @Query("SELECT new com.example.projectbase.domain.dto.response.GetProductsResponseDto(p.productId,p.name,p.featuredImage,p.price,p.discount,p.quantity) FROM Product p ORDER BY (p.price-p.discount*p.price/100) ASC")
    Page<GetProductsResponseDto> getProductsSortByTotal(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.quantity=?2 ,p.selled=?3, p.lastModifiedDate=CURRENT_TIMESTAMP WHERE p.productId=?1 ")
    void updateQuantity(int productId, int quantity,int selled);

    @Query("SELECT new com.example.projectbase.domain.dto.response.ProductFromCartResponseDto(p.productId,p.name,p.featuredImage,p.price,p.discount,p.quantity) FROM Product p WHERE p.productId <> ?1 AND p.author LIKE %?2% ")
    List<ProductFromCartResponseDto> getProductSameAuthor(int productId, String author);

    @Query("SELECT SUM(p.quantity) FROM Product p")
    int getQuantityProducts();

    @Query("SELECT p FROM Product p WHERE (p.name LIKE %?1%) AND(p.category.name=?2) AND (p.quantity IN (?3,?4)) AND (p.selled IN (?5,?6)) ")
    Page<Product> findProductsAdmin(String name,String catrName,int startQuan,int endQuan,int startSell,int endSell,Pageable pageable);
}
