package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.SuccessMessage;
import com.example.projectbase.domain.dto.CategoryDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.entity.Category;

import com.example.projectbase.exception.NotFoundException;
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

    @Override
    public CommonResponseDto updateCategory(int categoryId, CategoryDto categoryDto) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID,new String[]{String.valueOf(categoryId)}));
        categoryRepository.updateCategory(categoryId,categoryDto.getName());
        return new CommonResponseDto(true,SuccessMessage.UPDATE);
    }

    @Override
    public CommonResponseDto deleteCategory(int categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID,new String[]{String.valueOf(categoryId)}));
        categoryRepository.delete(category);
        return new CommonResponseDto(true,SuccessMessage.DELETE);
    }
}
