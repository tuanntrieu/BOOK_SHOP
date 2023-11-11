package com.example.projectbase.service.impl;

import com.example.projectbase.domain.dto.CategoryDto;
import com.example.projectbase.domain.entity.Category;

import com.example.projectbase.repository.CategoryRepository;
import com.example.projectbase.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
