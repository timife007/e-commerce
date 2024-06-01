package com.timife.services.impl;

import com.timife.model.dtos.SectionDto;
import com.timife.model.entities.Section;
import com.timife.model.mappers.Mapper;
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
    public SectionDto createSection(SectionDto sectionDto) {
        var currentSection = sectionRepository.findSectionByNameAndCategory(sectionDto.getName(), sectionDto.getCategoryId());

        if (currentSection == null) {
            Section newSection = Section.builder().sectionName(sectionDto.getName()).build();
            return sectionDtoMapper.mapTo(sectionRepository.save(newSection));
        }
        throw new IllegalArgumentException("Section already saved");
    }

    @Override
    public SectionDto updateSection(int sectionId, SectionDto sectionDto) {

        Section oldSection = sectionRepository.findById((long) sectionId).orElseThrow();
        oldSection.setSectionName(sectionDto.getName());
        oldSection.setCategoryId(sectionDto.getCategoryId());
        return sectionDtoMapper.mapTo(sectionRepository.save(oldSection));
    }

    @Override
    public List<SectionDto> getAllSections() {
        return sectionRepository.findAll().stream().map(sectionDtoMapper::mapTo).toList();
    }
}
