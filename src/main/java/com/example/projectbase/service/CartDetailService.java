package com.example.projectbase.service;

import com.example.projectbase.domain.dto.CartDetailDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.dto.response.ProductFromCartResponseDto;

import java.util.List;

public interface CartDetailService {
    CommonResponseDto addProductToCart(int customerId, CartDetailDto cartDetailDto);

    List<ProductFromCartResponseDto> getCartInfor(int customerId);

    CommonResponseDto updateCartInfor(int customerId,CartDetailDto cartDetailDto);

    CommonResponseDto deleteProductFromCart(int customerId,int productId);


}
