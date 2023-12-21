package com.example.projectbase.domain.dto.response;

import com.example.projectbase.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductsResponseDto {

    private int productId;

    private String name;

    private String image;

    private String author;

    private int quantity;

    private int price;

    private String description;

    private float discount;

    private String size;

    private int selled;

    Category category;

    public GetProductsResponseDto(int productId, String name, String image, int quantity, int price, float discount,Category category) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.category=category;
    }

    public GetProductsResponseDto(int productId, String name, String image, int price, float discount, int quantity,Category category) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.category=category;
    }


}
