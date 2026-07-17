package com.subhash.ims.service;

import com.subhash.ims.dto.request.CategoryRequestDto;
import com.subhash.ims.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto request);

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto request);

    void deleteCategory(Long id);

    CategoryResponseDto getCategoryById(Long id);

    List<CategoryResponseDto> getAllCategories();

    List<CategoryResponseDto> getRootCategories();

    List<CategoryResponseDto> getSubCategories(Long parentId);
}
