package com.example.projectbase.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequestDto {
    private int id;
    private String name;
    private String author;
    private int cate_id;
    private String description;
    private int discount;
    private int price;
    private int quantity;
    private String size;
    private List<String> images;
}
