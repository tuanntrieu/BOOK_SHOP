package com.example.projectbase.service;

import com.example.projectbase.domain.dto.CategoryDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(int categoryId, CategoryDto categoryDto);

    List<Category> getCategories();

    CommonResponseDto updateCategory(int categoryId, CategoryDto categoryDto);

    CommonResponseDto deleteCategory(int categoryId);

    Category getCategory(int categoryId);
}
