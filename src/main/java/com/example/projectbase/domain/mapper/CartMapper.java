package com.example.projectbase.domain.mapper;


import com.example.projectbase.domain.dto.CartDto;
import com.example.projectbase.domain.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart toCart(CartDto cartDto);
}