package com.timife.model.mappers.impl;

import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.SectionDto;
import com.timife.model.entities.Category;
import com.timife.model.entities.Section;
import com.timife.model.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SectionMapper implements Mapper<Section, SectionDto> {

    private final ModelMapper modelMapper;
    @Override
    public SectionDto mapTo(Section section) {
        return modelMapper.map(section, SectionDto.class);
    }

    @Override
    public Section mapFrom(SectionDto sectionDto) {
        return modelMapper.map(sectionDto, Section.class);
    }
}
