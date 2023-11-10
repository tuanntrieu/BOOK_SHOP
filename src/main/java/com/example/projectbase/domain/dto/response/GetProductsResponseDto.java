package com.example.projectbase.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductsResponseDto {

    private int productID;

    private  String name;

    private String image;

    private String author;

    private int quantity;

    private int price;

    private String description;

    private float discount;

    private String size;



    public GetProductsResponseDto(int productID, String name, String image, int price, float discount) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.discount = discount;
    }


}
