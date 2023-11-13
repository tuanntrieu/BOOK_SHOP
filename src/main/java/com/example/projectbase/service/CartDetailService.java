package com.example.projectbase.service;

import com.example.projectbase.domain.dto.CartDetailDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;

import java.util.List;

public interface CartDetailService {
    CommonResponseDto addProductToCart(String userId, CartDetailDto cartDetailDto);

    List<GetProductsResponseDto> getCartInfor(String userId);

    CommonResponseDto updateCartInfor(String userId,CartDetailDto cartDetailDto);

    CommonResponseDto deleteProductFromCart(String userId,int productId);


}
