package com.timife.services;

import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.SectionDto;
import com.timife.model.entities.Category;

import java.util.List;

public interface SectionService {


    SectionDto createSection(SectionDto sectionDto);

    SectionDto updateSection(int sectionId, SectionDto sectionDto);

    List<SectionDto> getAllSections();
}
