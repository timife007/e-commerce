package com.timife.services;

import com.timife.model.dtos.SubCategoryDto;
import com.timife.model.entities.Category;
import com.timife.model.entities.GenderCategory;
import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.GenderDto;
import com.timife.model.entities.SubCategory;

import java.util.List;

public interface CategoryService {

    GenderCategory createUpdateGenderCategory(GenderDto genderDto);

    List<GenderCategory> getAllGenders();

    Category createUpdateCategory(CategoryDto categoryDto);

    SubCategory createUpdateSubCategory(SubCategoryDto subCategoryDto);

    List<Category> getAllCategories();

    List<SubCategory> getAllSubCategories();
}
