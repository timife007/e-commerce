package com.timife.services.impl;

import com.timife.model.entities.Category;
import com.timife.model.entities.Gender;
import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.GenderDto;
import com.timife.model.mappers.Mapper;
import com.timife.model.responses.CategoryResponse;
import com.timife.repositories.CategoryRepository;
import com.timife.repositories.GenderRepository;
import com.timife.services.CategoryService;
import com.timife.utils.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final GenderRepository genderRepository;

//    @Autowired
//    private final Mapper<Category, CategoryDto> categoryDtoMapper;


    @Override
    public CategoryResponse createCategory(CategoryDto categoryDto) {
        Gender gender = genderRepository.findById(categoryDto.getGenderId()).orElseThrow();
        Category newCategory = Category.builder().name(categoryDto.getName()).gender(gender).build();
        Category savedCategory = categoryRepository.save(newCategory);
        return CategoryResponse.builder().id(savedCategory.getId()).name(savedCategory.getName()).genderId(gender.getId()).build();
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
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map((category) -> CategoryResponse.builder().id(category.getId()).name(category.getName()).genderId(category.getGender().getId()).build())
                .toList();
    }
}
