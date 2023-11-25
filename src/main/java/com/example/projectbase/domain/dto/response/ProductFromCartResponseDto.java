package com.example.projectbase.domain.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductFromCartResponseDto {
    private int productId;

    private String name;

    private String image;

    private int price;

    private float discount;

    private int quantity;
}
