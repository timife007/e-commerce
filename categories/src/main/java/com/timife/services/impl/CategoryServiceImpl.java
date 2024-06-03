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

//    @Autowired
//    private final Mapper<Category, CategoryDto> categoryDtoMapper;


    @Override
    public CategoryResponse createCategory(CategoryDto categoryDto) {
        Category currentCategory = categoryRepository.findCategoryByNameAndGender(categoryDto.getName(), categoryDto.getGenderId());
        if (currentCategory == null) {
            Category newCategory = Category.builder().name(categoryDto.getName()).genderId(categoryDto.getGenderId()).build();
            Category savedCategory = categoryRepository.save(newCategory);
            return CategoryResponse.builder().id(savedCategory.getId()).name(savedCategory.getName()).genderId(savedCategory.getGenderId()).build();
        } else {
            throw new CustomException("Category already created", HttpStatus.CREATED);
        }
    }

    @Override
    public CategoryResponse updateCategory(int categoryId, CategoryDto categoryDto) {
        Category currentCategory = categoryRepository.findById((long) categoryId).orElseThrow();

        currentCategory.setName(categoryDto.getName());
        currentCategory.setGenderId(categoryDto.getGenderId());
        Category savedCategory = categoryRepository.save(currentCategory);
        return CategoryResponse.builder().id((long) categoryId)
                .name(savedCategory.getName())
                .genderId(savedCategory.getGenderId())
                .build();
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map((category) -> CategoryResponse.builder().id(category.getId()).name(category.getName()).genderId(category.getGenderId()).build())
                .toList();
    }
}
