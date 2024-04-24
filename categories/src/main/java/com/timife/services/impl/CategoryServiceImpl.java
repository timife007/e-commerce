package com.timife.services.impl;

import com.timife.model.dtos.SubCategoryDto;
import com.timife.model.entities.Category;
import com.timife.model.entities.GenderCategory;
import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.GenderDto;
import com.timife.model.entities.SubCategory;
import com.timife.repositories.CategoryRepository;
import com.timife.repositories.GenderRepository;
import com.timife.repositories.SubCategoryRepository;
import com.timife.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final GenderRepository genderRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public GenderCategory createUpdateGenderCategory(GenderDto genderDto) {

        GenderCategory currentCategory = genderRepository.findByName(genderDto.getName());

        if (currentCategory == null) {
            GenderCategory newGender = GenderCategory
                    .builder()
                    .name(genderDto.getName())
                    .build();
            return genderRepository.save(newGender);
        }
        currentCategory.setName(genderDto.getName());
        return genderRepository.save(currentCategory);
    }

    @Override
    public List<GenderCategory> getAllGenders() {
        return genderRepository.findAll();
    }

    @Override
    public Category createUpdateCategory(CategoryDto categoryDto) {
        Category currentCategory = categoryRepository.findByNameAndGender(categoryDto.getName(), categoryDto.getGenderId());
        GenderCategory genderCategory = genderRepository.findById(categoryDto.getGenderId()).orElseThrow();

        if (currentCategory == null) {
            Category newCategory = Category
                    .builder()
                    .name(categoryDto.getName())
                    .genderCategory(genderCategory)
                    .build();
            return categoryRepository.save(newCategory);
        }
        currentCategory.setName(categoryDto.getName());
        currentCategory.setGenderCategory(genderCategory);
        return categoryRepository.save(currentCategory);
    }

    @Override
    public SubCategory createUpdateSubCategory(SubCategoryDto subCategoryDto) {
        SubCategory currentSubCategory = subCategoryRepository.findSubCategoryByCategoryAndGender(subCategoryDto.getName(), subCategoryDto.getGenderId(), subCategoryDto.getCategoryId());
        Category category = categoryRepository.findById(subCategoryDto.getCategoryId()).orElseThrow();
        GenderCategory genderCategory = genderRepository.findById(subCategoryDto.getGenderId()).orElseThrow();

        if (currentSubCategory == null) {
            SubCategory subCategory = SubCategory
                    .builder()
                    .genderCategory(genderCategory)
                    .category(category)
                    .build();
            return subCategoryRepository.save(subCategory);
        }
        currentSubCategory.setName(subCategoryDto.getName());
        currentSubCategory.setCategory(category);
        currentSubCategory.setGenderCategory(genderCategory);
        return subCategoryRepository.save(currentSubCategory);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }
}
