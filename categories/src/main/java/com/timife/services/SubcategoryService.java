package com.timife.services;

import com.timife.model.dtos.SubcategoryDto;
import com.timife.model.responses.SubcategoryResponse;

import java.util.List;

public interface SubcategoryService {


    SubcategoryResponse createSubcategory(SubcategoryDto subcategoryDto);

    SubcategoryResponse updateSubcategory(Long subcategoryId, SubcategoryDto subcategoryDto);

    List<SubcategoryResponse> getAllSubcategories();
}
