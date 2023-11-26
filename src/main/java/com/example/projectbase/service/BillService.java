package com.example.projectbase.service;

import com.example.projectbase.domain.dto.BillDto;
import com.example.projectbase.domain.dto.request.BuyNowRequestDto;
import com.example.projectbase.domain.dto.request.PlaceOrderRequestDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;


public interface BillService {
    CommonResponseDto placeOrderFromCart(int customerId, PlaceOrderRequestDto requestDto);

    CommonResponseDto buyNow(int customerId, BuyNowRequestDto requestDto);

    CommonResponseDto cancelOrder(int billId);

    CommonResponseDto buyAgain(int billId);
}
