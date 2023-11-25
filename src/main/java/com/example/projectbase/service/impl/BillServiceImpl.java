package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.domain.dto.request.PlaceOrderRequestDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.entity.Cart;
import com.example.projectbase.domain.entity.User;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.repository.CartRepository;
import com.example.projectbase.repository.UserRepository;
import com.example.projectbase.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final UserRepository userRepository;

    private final CartRepository cartRepository;



//    @Override
//    public CommonResponseDto placeOrderFromCart(String userId, PlaceOrderRequestDto requestDto) {
//        Optional<User> user = userRepository.findById(userId);
//        if (user.isEmpty()) {
//            throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId});
//        }
//
//        Optional<Cart> cart = cartRepository.findById(user.get().getCustomer().getCart().getId());
//        if (cart.isEmpty()) {
//            throw new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_ID, new String[]{String.valueOf(user.get().getCustomer().getCart().getId())});
//        }
//
//
//
//
//        return null;
//    }
}
