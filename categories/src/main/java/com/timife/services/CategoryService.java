package com.timife.services;

import com.timife.model.dtos.CategoryDto;
import com.timife.model.responses.CategoryResponse;

import java.util.List;

public interface CategoryService {



    CategoryResponse createCategory(CategoryDto categoryDto);

    CategoryResponse updateCategory(Long categoryId, CategoryDto categoryDto);

    List<CategoryResponse> getAllCategories();

}
