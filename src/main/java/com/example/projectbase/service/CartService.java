package com.example.projectbase.service;

import com.example.projectbase.domain.dto.CartDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;

public interface CartService {
    CommonResponseDto createCartForCustomer(CartDto cartDto);


}
