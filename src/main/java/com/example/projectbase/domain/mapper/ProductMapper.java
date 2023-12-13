package com.example.projectbase.domain.mapper;


import com.example.projectbase.domain.dto.ProductDto;
import com.example.projectbase.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMapper {


  //  @Mapping(target = "image", ignore = true)
    Product toProduct(ProductDto productDto);
}
