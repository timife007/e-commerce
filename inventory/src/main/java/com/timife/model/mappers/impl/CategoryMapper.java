package com.timife.model.mappers.impl;

import com.timife.model.dtos.CategoryDto;
import com.timife.model.entities.Category;
import com.timife.model.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper implements Mapper<Category, CategoryDto> {

    private final ModelMapper modelMapper;
    @Override
    public CategoryDto mapTo(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public Category mapFrom(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
}
