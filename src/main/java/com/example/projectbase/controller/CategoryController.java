package com.example.projectbase.controller;

import com.example.projectbase.base.RestApiV1;
import com.example.projectbase.base.VsResponseUtil;
import com.example.projectbase.constant.UrlConstant;
import com.example.projectbase.domain.dto.CategoryDto;
import com.example.projectbase.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API create category")
    @PostMapping(UrlConstant.Category.CREATE_CATEGORY)
    public ResponseEntity<?> createCategory(@PathVariable int categoryId, @Valid @RequestBody CategoryDto categoryDto) {
        return VsResponseUtil.success(categoryService.createCategory(categoryId, categoryDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API update category")
    @PutMapping(value = UrlConstant.Category.UPDATE_CATEGORY, consumes = "multipart/form-data")
    public ResponseEntity<?> updateCustomer(@PathVariable int categoryId, @Valid @ModelAttribute CategoryDto categoryDto) {
        return VsResponseUtil.success(categoryService.updateCategory(categoryId, categoryDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API delete category")
    @DeleteMapping(UrlConstant.Category.DELETE_CATEGORY)
    public ResponseEntity<?> deleteCategory(@PathVariable int categoryId) {
        return VsResponseUtil.success(categoryService.deleteCategory(categoryId));
    }


}
