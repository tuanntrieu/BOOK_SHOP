package com.example.projectbase.service;

import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.request.BuyNowRequestDto;
import com.example.projectbase.domain.dto.request.PlaceOrderRequestDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.entity.Bill;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;


public interface BillService {
    CommonResponseDto placeOrderFromCart(int customerId, PlaceOrderRequestDto requestDto);

    CommonResponseDto buyNow(int customerId, BuyNowRequestDto requestDto);

    CommonResponseDto cancelOrder(int customerId, int billId);

    CommonResponseDto buyAgain(int customerId, int billId);

    CommonResponseDto comfirmOrder(int billId);

    PaginationResponseDto<Bill> getBills(int customerId, PaginationFullRequestDto request);

    Bill getBillInfor(int billId);

    int countBill();

    long getRevenue();

    List<Integer> getCoutBillByStatus();

    List<Bill> getBillsToPay();

    PaginationResponseDto getAllBills(PaginationFullRequestDto requestDto, String status);

    CommonResponseDto getsBillSatistics(HttpServletResponse response, Date timeStart, Date timeEnd) throws IOException;

    CommonResponseDto updateOrderStatus(int billId, String status);
}
