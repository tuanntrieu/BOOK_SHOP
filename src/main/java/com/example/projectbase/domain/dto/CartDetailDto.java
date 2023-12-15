package com.example.projectbase.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDetailDto {

    private int productId;


    @Min(value = 1)
    private int quantity;
}
