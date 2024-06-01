package com.timife.services.impl;

import com.timife.model.dtos.GenderDto;
import com.timife.model.entities.Category;
import com.timife.model.entities.Gender;
import com.timife.model.mappers.Mapper;
import com.timife.repositories.GenderRepository;
import com.timife.services.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RequiredArgsConstructor
public class GenderServiceImpl implements GenderService {

    @Autowired
    private final GenderRepository genderRepository;

    private final Mapper<Gender, GenderDto> modelMapper;

    @Override
    public List<GenderDto> getAllGenders() {
        return genderRepository.findAll().stream().map(modelMapper::mapTo).toList();
    }

    @Override
    public GenderDto createGender(GenderDto genderDto) {

        Gender currentGender = genderRepository.findByName(genderDto.getName());

        if (currentGender == null) {
            Gender newGender = modelMapper.mapFrom(genderDto);
            return modelMapper.mapTo(genderRepository.save(newGender));
        }
        throw new IllegalArgumentException("Gender already present");
    }

    @Override
    public GenderDto updateGender(int genderId, GenderDto genderDto) {
        Gender newGender = genderRepository.findById((long) genderId).orElseThrow();
        newGender.setName(genderDto.getName());
        return modelMapper.mapTo(genderRepository.save(newGender));
    }
}
