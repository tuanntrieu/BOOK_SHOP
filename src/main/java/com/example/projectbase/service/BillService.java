package com.example.projectbase.service;

import com.example.projectbase.domain.dto.BillDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.request.BuyNowRequestDto;
import com.example.projectbase.domain.dto.request.PlaceOrderRequestDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.entity.Bill;


public interface BillService {
    CommonResponseDto placeOrderFromCart(int customerId, PlaceOrderRequestDto requestDto);

    CommonResponseDto buyNow(int customerId, BuyNowRequestDto requestDto);

    CommonResponseDto cancelOrder(int billId);

    CommonResponseDto buyAgain(int billId);

    PaginationResponseDto<Bill> getBills(int customerId,PaginationFullRequestDto request);

    Bill getBillInfor(int billId);
}
