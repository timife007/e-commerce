package com.timife.services;

import com.timife.model.GenderCategory;
import com.timife.model.dtos.GenderDto;

public interface CategoryService {

    GenderCategory createUpdateCategory(GenderDto genderDto);
}
