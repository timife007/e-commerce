package com.timife.services.impl;

import com.timife.model.dtos.SectionDto;
import com.timife.model.entities.Section;
import com.timife.model.mappers.Mapper;
import com.timife.model.responses.SectionResponse;
import com.timife.repositories.SectionRepository;
import com.timife.services.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private final SectionRepository sectionRepository;

    @Autowired
    private final Mapper<Section, SectionDto> sectionDtoMapper;

    @Override
    public SectionResponse createSection(SectionDto sectionDto) {
        var currentSection = sectionRepository.findSectionByNameAndCategory(sectionDto.getName(), sectionDto.getCategoryId());

        if (currentSection == null) {
            Section newSection = Section.builder().sectionName(sectionDto.getName()).build();
            Section savedSection = sectionRepository.save(newSection);

            return SectionResponse.builder().id(savedSection.getId()).name(savedSection.getSectionName()).categoryId(savedSection.getCategoryId()).build();
        }
        throw new IllegalArgumentException("Section already saved");
    }

    @Override
    public SectionResponse updateSection(int sectionId, SectionDto sectionDto) {

        Section oldSection = sectionRepository.findById((long) sectionId).orElseThrow();
        oldSection.setSectionName(sectionDto.getName());
        oldSection.setCategoryId(sectionDto.getCategoryId());
        Section savedSection = sectionRepository.save(oldSection);
        return SectionResponse.builder().id(savedSection.getId()).name(savedSection.getSectionName()).categoryId(savedSection.getCategoryId()).build();
    }

    @Override
    public List<SectionResponse> getAllSections() {
        return sectionRepository.findAll().stream()
                .map((section) -> SectionResponse.builder().id(section.getId()).name(section.getSectionName()).categoryId(section.getCategoryId()).build())
                .toList();
    }
}
