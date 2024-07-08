package com.timife.services.impl;

import com.timife.model.entities.Category;
import com.timife.model.entities.Gender;
import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.GenderDto;
import com.timife.model.mappers.Mapper;
import com.timife.model.responses.CategoryListResponse;
import com.timife.model.responses.CategoryResponse;
import com.timife.repositories.CategoryRepository;
import com.timife.repositories.GenderRepository;
import com.timife.services.CategoryService;
import com.timife.utils.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final GenderRepository genderRepository;


    @Override
    public CategoryResponse createCategory(CategoryDto categoryDto) {
        var savedGender = genderRepository.findById(categoryDto.getGenderId()).orElseThrow();
        var savedCategory = categoryRepository.findByNameAndGender(categoryDto.getName(), savedGender);

        if (savedCategory == null) {
            Gender gender = genderRepository.findById(categoryDto.getGenderId()).orElseThrow();
            Category newCategory = Category.builder().name(categoryDto.getName()).gender(gender).build();
            log.error(newCategory.getName());
            if (categoryDto.getParentId() == null) {
                gender.getCategories().add(newCategory);
                genderRepository.save(gender);
            } else {
                log.error("This line is evoked");
                Category parentCategory = categoryRepository.findById(categoryDto.getParentId()).orElseThrow();
                newCategory.setParentCategory(parentCategory);
                Category saveCategory = categoryRepository.save(newCategory);
                return CategoryResponse.builder().id(saveCategory.getId()).name(saveCategory.getName()).parentId(parentCategory.getId()).genderId(saveCategory.getGender().getId()).build();
            }
            var category = categoryRepository.findByNameAndGender(categoryDto.getName(), gender);
            return CategoryResponse.builder().id(category.getId()).genderId(category.getGender().getId()).name(category.getName()).build();
        } else {
            throw new IllegalArgumentException("Category already present");
        }
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category currentCategory = categoryRepository.findById((long) categoryId).orElseThrow();
        Gender gender = genderRepository.findById(categoryDto.getGenderId()).orElseThrow();

        currentCategory.setName(categoryDto.getName());
        currentCategory.setGender(gender);
        Category savedCategory = categoryRepository.save(currentCategory);
        return CategoryResponse.builder().id(categoryId)
                .name(savedCategory.getName())
                .genderId(gender.getId())
                .build();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
