package com.example.projectbase.repository;

import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.dto.response.ProductFromCartResponseDto;
import com.example.projectbase.domain.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {

    @Query("SELECT new com.example.projectbase.domain.dto.response.ProductFromCartResponseDto(cd.product.productId,cd.product.name,cd.product.image,cd.product.price,cd.product.discount,cd.quantity) FROM CartDetail cd WHERE cd.cart.id=?1 ORDER BY cd.createdDate DESC")
    List<ProductFromCartResponseDto> getCartInfor(int cartId);

    @Transactional
    @Modifying
    @Query("UPDATE CartDetail cd SET cd.quantity=?3 WHERE cd.cart.id=?1 AND cd.product.productId=?2")
    void updateCartInfor(int cartId, int productId, int quantity);

    @Query("SELECT COALESCE(SUM(cd.quantity), 0) FROM CartDetail cd WHERE cd.cart.id = ?1")
    int getTotalProductsInCart(int cartId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartDetail cd WHERE cd.cart.id=?1 AND cd.product.productId=?2")
    void deleteCartDetail(int cartId, int productId);

    @Query("SELECT new com.example.projectbase.domain.dto.response.ProductFromCartResponseDto(cd.product.productId,cd.product.name,cd.product.image,cd.product.price,cd.product.discount,cd.quantity) FROM CartDetail cd WHERE cd.cart.id=?1 AND cd.product.productId=?2")
    Optional<ProductFromCartResponseDto> getProductFromCart(int cartId, int productId);
}
