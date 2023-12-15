package com.example.projectbase.service.impl;

import com.example.projectbase.constant.SuccessMessage;
import com.example.projectbase.domain.dto.CartDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.entity.Cart;
import com.example.projectbase.domain.mapper.CartMapper;
import com.example.projectbase.repository.CartRepository;
import com.example.projectbase.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    @Override
    public CommonResponseDto createCartForCustomer(CartDto cartDto) {

        Cart cart = cartRepository.save(cartMapper.toCart(cartDto));
        cartRepository.addCartForCustomer(cart.getId(), cartDto.getCustomerId());
        return new CommonResponseDto(true, SuccessMessage.GENERATE_CART);

    }
}
