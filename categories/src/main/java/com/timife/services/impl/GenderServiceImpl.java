package com.timife.services.impl;

import com.timife.model.dtos.GenderDto;
import com.timife.model.entities.Category;
import com.timife.model.entities.Gender;
import com.timife.model.mappers.Mapper;
import com.timife.model.responses.CategoryResponse;
import com.timife.model.responses.GenderResponse;
import com.timife.repositories.CategoryRepository;
import com.timife.repositories.GenderRepository;
import com.timife.services.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenderServiceImpl implements GenderService {

    @Autowired
    private final GenderRepository genderRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final Mapper<Gender, GenderDto> modelMapper;

    @Override
    public List<GenderResponse> getAllGenders() {
        return genderRepository.findAll()
                .stream()
                .map((gender) -> GenderResponse.builder().id(gender.getId()).name(gender.getName()).categories(gender.getCategories().stream().map((category) ->
                        CategoryResponse.builder().id(category.getId()).genderId(gender.getId()).name(category.getName()).build()
                ).toList()).build())
                .toList();
    }

    @Override
    public GenderResponse createGender(GenderDto genderDto) {

        Gender currentGender = genderRepository.findByName(genderDto.getName());

        if (currentGender == null) {
            Gender newGender = Gender.builder().name(genderDto.getName()).build();
            Gender savedGender = genderRepository.save(newGender);
            return GenderResponse.builder()
                    .id(savedGender.getId())
                    .name(savedGender.getName())
                    .build();
        }
        throw new IllegalArgumentException("Gender already present");
    }

    @Override
    public GenderResponse updateGender(Long genderId, GenderDto genderDto) {
        Gender newGender = genderRepository.findById((long) genderId).orElseThrow();
        newGender.setName(genderDto.getName());
        Gender savedGender = genderRepository.save(newGender);
        return GenderResponse.builder().id(genderId).name(savedGender.getName()).build();
    }
}
