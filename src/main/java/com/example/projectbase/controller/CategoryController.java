package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.CategoryDto;
import com.example.projectbase.domain.dto.CustomerDto;
import com.example.projectbase.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "API get categories")
    @GetMapping(UrlConstant.Category.GET_CATEGORIES)
    public ResponseEntity<?> getCategories() {
        return VsResponseUtil.success(categoryService.getCategories());
    }

    @Operation(summary = "API update category")
    @PutMapping(UrlConstant.Category.UPDATE_CATEGORY)
    public ResponseEntity<?> updateCustomer(@PathVariable int categoryId, @Valid @RequestBody CategoryDto categoryDto) {
        return VsResponseUtil.success(categoryService.updateCategory(categoryId, categoryDto));
    }


    @Operation(summary = "API delete category")
    @DeleteMapping(UrlConstant.Category.DELETE_CATEGORY)
    public ResponseEntity<?> deleteCategory(@PathVariable int categoryId){
        return VsResponseUtil.success(categoryService.deleteCategory(categoryId));
    }


}
