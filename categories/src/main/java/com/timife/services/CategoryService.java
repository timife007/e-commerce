package com.timife.services;

import com.timife.model.entities.Category;
import com.timife.model.entities.GenderCategory;
import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.GenderDto;

public interface CategoryService {

    GenderCategory createUpdateGenderCategory(GenderDto genderDto);

    Category createUpdateCategory(CategoryDto categoryDto);
}
