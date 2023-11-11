package com.example.projectbase.domain.mapper;

import com.example.projectbase.domain.dto.CategoryDto;
import com.example.projectbase.domain.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "image", ignore = true)
    Category toCategory(CategoryDto categoryDto);
}
