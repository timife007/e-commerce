package com.timife.services.impl;

import com.timife.model.entities.Category;
import com.timife.model.entities.Gender;
import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.GenderDto;
import com.timife.model.mappers.Mapper;
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

    private final Mapper<Category, CategoryDto> categoryDtoMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category currentCategory = categoryRepository.findCategoryByNameAndGender(categoryDto.getName(),categoryDto.getGenderId());
        if (currentCategory == null) {
            Category newCategory = categoryDtoMapper.mapFrom(categoryDto);
            return categoryDtoMapper.mapTo(categoryRepository.save(newCategory));
        } else {
            throw new CustomException("Category already created", HttpStatus.CREATED);
        }
    }

    @Override
    public CategoryDto updateCategory(int categoryId, CategoryDto categoryDto) {
        Category currentCategory = categoryRepository.findById((long) categoryId).orElseThrow();

        currentCategory.setName(categoryDto.getName());
        currentCategory.setGenderId(categoryDto.getGenderId());
        return categoryDtoMapper.mapTo(categoryRepository.save(currentCategory));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryDtoMapper::mapTo).toList();
    }
}
