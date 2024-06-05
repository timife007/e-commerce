package com.timife.services;

import com.timife.model.dtos.SubCategoryDto;
import com.timife.model.entities.Category;
import com.timife.model.entities.Gender;
import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.GenderDto;
import com.timife.model.responses.CategoryResponse;

import java.util.List;

public interface CategoryService {



    CategoryResponse createCategory(CategoryDto categoryDto);

    CategoryResponse updateCategory(Long categoryId, CategoryDto categoryDto);

    List<CategoryResponse> getAllCategories();

}
