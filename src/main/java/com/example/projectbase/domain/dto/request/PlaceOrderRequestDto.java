package com.example.projectbase.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlaceOrderRequestDto {
    List<Integer> listProductId;
}
