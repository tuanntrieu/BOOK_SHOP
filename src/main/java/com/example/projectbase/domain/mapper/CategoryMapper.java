package com.example.projectbase.domain.mapper;

import com.example.projectbase.domain.dto.CategoryDto;
import com.example.projectbase.domain.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryDto categoryDto);
}
