package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.CartDetailDto;
import com.example.projectbase.service.CartDetailService;
import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class CartController {
    private final CartDetailService cartDetailService;

    @Operation(summary = "API get cart infor")
    @GetMapping(UrlConstant.Cart.GET_CART_INFOR)
    public ResponseEntity<?> getCartInfor(@PathVariable String userId){
        return VsResponseUtil.success(cartDetailService.getCartInfor(userId));
    }

    @Operation(summary = "API update cart infor")
    @PutMapping(UrlConstant.Cart.UPDATE_CART_INFOR)
    public ResponseEntity<?> updateCartInfor(@PathVariable String userId, @Valid @RequestBody CartDetailDto cartDetailDto){
        return VsResponseUtil.success(cartDetailService.updateCartInfor(userId,cartDetailDto));
    }
    @Operation(summary = "API delete product from cart")
    @DeleteMapping(UrlConstant.Cart.DELETE_PRODUCT_FROM_CART)
    public ResponseEntity<?>deleteProductFromCart(@PathVariable String userId,@PathVariable int productId){
        return VsResponseUtil.success(cartDetailService.deleteProductFromCart(userId,productId));
    }
}
