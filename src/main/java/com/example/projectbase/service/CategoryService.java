package com.example.projectbase.service;

import com.example.projectbase.domain.dto.CategoryDto;
import com.example.projectbase.domain.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);

    List<Category> getCategories();
}
