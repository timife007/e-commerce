package com.timife.services.impl;

import com.timife.model.entities.Category;
import com.timife.model.entities.Gender;
import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.GenderDto;
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
    private final GenderRepository genderRepository;

    @Autowired
    private final CategoryRepository categoryRepository;


    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Category currentCategory = categoryRepository.findCategoryByGender(categoryDto.getGenderId());
        Gender gender = genderRepository.findById(categoryDto.getGenderId()).orElseThrow();

        if (currentCategory == null) {
            Category newCategory = Category
                    .builder()
                    .name(categoryDto.getName())
                    .genderId(categoryDto.getGenderId())
                    .build();
            return categoryRepository.save(newCategory);
        } else {
            throw new CustomException("Category already created", HttpStatus.CREATED);
        }
    }

    @Override
    public Category updateCategory(int categoryId, CategoryDto categoryDto) {
        Category newCategory = categoryRepository.findById((long) categoryId).orElseThrow();

        newCategory.setName(categoryDto.getName());
        newCategory.setGenderId(categoryDto.getGenderId());
        return categoryRepository.save(newCategory);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
