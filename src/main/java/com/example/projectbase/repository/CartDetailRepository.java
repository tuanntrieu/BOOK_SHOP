package com.example.projectbase.repository;

import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail,Integer> {

    @Query("SELECT new com.example.projectbase.domain.dto.response.GetProductsResponseDto(cd.product.productID,cd.product.name,cd.product.image,cd.product.price,cd.product.discount,cd.quantity) FROM CartDetail cd WHERE cd.cart.id=?1")
    List<GetProductsResponseDto> getCartInfor(int cartId);

    @Transactional
    @Modifying
    @Query("UPDATE CartDetail cd SET cd.quantity=?3 WHERE cd.cart.id=?1 AND cd.product.productID=?2")
    void updateCartInfor(int cartId,int prodcutId,int quantity);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartDetail cd WHERE cd.cart.id=?1 AND cd.product.productID=?2")
    void deleteCartDetail(int cartId,int productId);
}
