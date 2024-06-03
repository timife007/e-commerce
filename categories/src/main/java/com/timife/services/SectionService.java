package com.timife.services;

import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.SectionDto;
import com.timife.model.entities.Category;
import com.timife.model.responses.SectionResponse;

import java.util.List;

public interface SectionService {


    SectionResponse createSection(SectionDto sectionDto);

    SectionResponse updateSection(int sectionId, SectionDto sectionDto);

    List<SectionResponse> getAllSections();
}
