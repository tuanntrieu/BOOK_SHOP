package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.request.BuyNowRequestDto;
import com.example.projectbase.domain.dto.request.PlaceOrderRequestDto;
import com.example.projectbase.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "API order from cart")
    @PostMapping(UrlConstant.Bill.ORDER_FROM_CART)
    public ResponseEntity<?> orderFromCart(@PathVariable int customerId, @Valid @RequestBody PlaceOrderRequestDto requestDto) {
        return VsResponseUtil.success(billService.placeOrderFromCart(customerId, requestDto));
    }

    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "API buy now")
    @PostMapping(UrlConstant.Bill.BUY_NOW)
    public ResponseEntity<?> buyNow(@PathVariable int customerId, @Valid @RequestBody BuyNowRequestDto requestDto) {
        return VsResponseUtil.success(billService.buyNow(customerId, requestDto));
    }

    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "API cancel order")
    @PatchMapping(UrlConstant.Bill.CANCEL_ORDER)
    public ResponseEntity<?> cancelOrder(@PathVariable int customerId, @PathVariable int billId) {
        return VsResponseUtil.success(billService.cancelOrder(customerId, billId));
    }

    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "API buy again")
    @PatchMapping(UrlConstant.Bill.BUY_AGAIN)
    public ResponseEntity<?> buyAgain(@PathVariable int customerId, @PathVariable int billId) {
        return VsResponseUtil.success(billService.buyAgain(customerId, billId));
    }

    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "API get bills")
    @GetMapping(UrlConstant.Bill.GET_BILLS)
    public ResponseEntity<?> getBills(@PathVariable int customerId, @ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(billService.getBills(customerId, requestDto));
    }

    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "API get bill infor")
    @GetMapping(UrlConstant.Bill.GET_BILL_INFOR)
    public ResponseEntity<?> getBillInfor(@PathVariable int billId) {
        return VsResponseUtil.success(billService.getBillInfor(billId));
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @Operation(summary = "API confirm order")
    @PatchMapping(UrlConstant.Bill.CONFIRM_ORDER)
    public ResponseEntity<?> confirmOrder(@PathVariable int billId) {
        return VsResponseUtil.success(billService.comfirmOrder(billId));
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @Operation(summary = "API get count bill")
    @GetMapping(UrlConstant.Bill.GET_COUNT_BILL)
    public ResponseEntity<?> getCountBill() {
        return VsResponseUtil.success(billService.countBill());
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @Operation(summary = "API get revenue")
    @GetMapping(UrlConstant.Bill.GET_REVENUE)
    public ResponseEntity<?> getRevenue() {
        return VsResponseUtil.success(billService.getRevenue());
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @Operation(summary = "API get revenue")
    @GetMapping(UrlConstant.Bill.GET_COUNT_BILL_TO_PAY)
    public ResponseEntity<?> getCountBillToPay() {
        return VsResponseUtil.success(billService.getCoutBillToPay());
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @Operation(summary = "API get revenue")
    @GetMapping(UrlConstant.Bill.GET_BILLS_TO_PAY)
    public ResponseEntity<?> getBillsToPay() {
        return VsResponseUtil.success(billService.getBillsToPay());
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @Operation(summary = "API get all bills")
    @GetMapping(UrlConstant.Bill.GET_ALL_BILLS)
    public ResponseEntity<?> getAllBills(@ParameterObject PaginationFullRequestDto requestDto, @RequestParam String status) {
        return VsResponseUtil.success(billService.getAllBills(requestDto, status));
    }
}
