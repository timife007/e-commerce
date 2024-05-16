package com.timife.services;

import com.timife.model.dtos.SubCategoryDto;
import com.timife.model.entities.Category;
import com.timife.model.entities.Gender;
import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.GenderDto;

import java.util.List;

public interface CategoryService {

    Gender createUpdateGender(GenderDto genderDto);

    List<Gender> getAllGenders();

    Category createCategory(CategoryDto categoryDto);

    Category updateCategory(int categoryId, CategoryDto categoryDto);

    List<Category> getAllCategories();

}
