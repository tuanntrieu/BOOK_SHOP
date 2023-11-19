package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.CartDetailDto;
import com.example.projectbase.service.CartDetailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class CartDetailController {
    private final CartDetailService cartDetailService;

    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "API add product to cart")
    @PostMapping(UrlConstant.CartDetail.ADD_PRODUCT_TO_CART)
    public ResponseEntity<?> addProductToCart(@PathVariable String userId, @Valid @RequestBody CartDetailDto cartDetailDto) {
        return VsResponseUtil.success(cartDetailService.addProductToCart(userId, cartDetailDto));
    }
}
