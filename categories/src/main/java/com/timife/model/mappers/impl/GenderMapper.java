package com.timife.model.mappers.impl;

import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.GenderDto;
import com.timife.model.entities.Category;
import com.timife.model.entities.Gender;
import com.timife.model.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenderMapper implements Mapper<Gender, GenderDto> {

    private final ModelMapper modelMapper;
    @Override
    public GenderDto mapTo(Gender gender) {
        return modelMapper.map(gender, GenderDto.class);
    }

    @Override
    public Gender mapFrom(GenderDto genderDto) {
        return modelMapper.map(genderDto, Gender.class);
    }
}
