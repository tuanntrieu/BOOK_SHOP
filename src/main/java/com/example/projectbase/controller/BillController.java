package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.BillDto;
import com.example.projectbase.domain.dto.request.BuyNowRequestDto;
import com.example.projectbase.domain.dto.request.PlaceOrderRequestDto;
import com.example.projectbase.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PreAuthorize(value = "hasAnyRole('USER')")
    @Operation(summary = "API order from cart")
    @PostMapping(UrlConstant.Bill.ORDER_FROM_CART)
    public ResponseEntity<?> orderFromCart(@PathVariable int customerId, @Valid @RequestBody PlaceOrderRequestDto requestDto) {
        return VsResponseUtil.success(billService.placeOrderFromCart(customerId, requestDto));
    }

    @PreAuthorize(value = "hasAnyRole('USER')")
    @Operation(summary = "API buy now")
    @PostMapping(UrlConstant.Bill.BUY_NOW)
    public ResponseEntity<?> buyNow(@PathVariable int customerId, @Valid @RequestBody BuyNowRequestDto requestDto) {
        return VsResponseUtil.success(billService.buyNow(customerId, requestDto));
    }

    @PreAuthorize(value = "hasAnyRole('USER')")
    @Operation(summary = "API cancel order")
    @PutMapping(UrlConstant.Bill.CANCEL_ORDER)
    public ResponseEntity<?> cancelOrder(@PathVariable int billId) {
        return VsResponseUtil.success(billService.cancelOrder(billId));
    }
    @PreAuthorize(value = "hasAnyRole('USER')")
    @Operation(summary = "API buy again")
    @PutMapping(UrlConstant.Bill.BUY_AGAIN)
    public ResponseEntity<?> buyAgain(@PathVariable int billId) {
        return VsResponseUtil.success(billService.buyAgain(billId));
    }

}
