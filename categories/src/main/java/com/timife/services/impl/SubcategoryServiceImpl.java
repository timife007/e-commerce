package com.timife.services.impl;

import com.timife.model.dtos.SubcategoryDto;
import com.timife.model.entities.Category;
import com.timife.model.entities.Subcategory;
import com.timife.model.mappers.Mapper;
import com.timife.model.responses.SubcategoryResponse;
import com.timife.repositories.CategoryRepository;
import com.timife.repositories.SubcategoryRepository;
import com.timife.services.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    private final SubcategoryRepository subcategoryRepository;

    @Autowired
    private final CategoryRepository categoryRepository;



    @Override
    public SubcategoryResponse createSubcategory(SubcategoryDto subcategoryDto) {
        var currentSection = subcategoryRepository.findByNameAndCategoryId(subcategoryDto.getName(), subcategoryDto.getCategoryId());

        if (currentSection == null) {
            Category savedCategory = categoryRepository.findById(subcategoryDto.getCategoryId()).orElseThrow();
            Subcategory newSubcategory = Subcategory.builder().name(subcategoryDto.getName()).category(savedCategory).build();

            Subcategory savedSubcategory = subcategoryRepository.save(newSubcategory);

            return SubcategoryResponse.builder().id(savedSubcategory.getId()).name(savedSubcategory.getName()).categoryId(subcategoryDto.getCategoryId()).build();
        }
        throw new IllegalArgumentException("Subcategory already saved");
    }

    @Override
    public SubcategoryResponse updateSubcategory(Long subcategoryId, SubcategoryDto subcategoryDto) {

        Subcategory oldSubcategory = subcategoryRepository.findById((long) subcategoryId).orElseThrow();
        Category category = categoryRepository.findById(subcategoryDto.getCategoryId()).orElseThrow();
        oldSubcategory.setName(subcategoryDto.getName());
        oldSubcategory.setCategory(category);
        Subcategory savedSubcategory = subcategoryRepository.save(oldSubcategory);
        return SubcategoryResponse.builder().id(savedSubcategory.getId()).name(savedSubcategory.getName()).build();
    }

    @Override
    public List<SubcategoryResponse> getAllSubcategories() {
        return subcategoryRepository.findAll().stream()
                .map((section) -> SubcategoryResponse.builder().id(section.getId()).name(section.getName()).categoryId(section.getCategory().getId()).build())
                .toList();
    }
}
