package com.example.projectbase.repository;

import com.example.projectbase.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductImage pi WHERE pi.product.productId=?1 AND pi.imageId=?2")
    void deleteProductImage(int productId, int imageId);
}
