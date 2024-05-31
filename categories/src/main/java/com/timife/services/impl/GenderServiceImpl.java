package com.timife.services.impl;

import com.timife.model.dtos.GenderDto;
import com.timife.model.entities.Category;
import com.timife.model.entities.Gender;
import com.timife.repositories.GenderRepository;
import com.timife.services.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RequiredArgsConstructor
public class GenderServiceImpl implements GenderService {

    @Autowired
    private final GenderRepository genderRepository;

    @Override
    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }

    @Override
    public Gender createUpdateGender(GenderDto genderDto) {

        Gender currentGender = genderRepository.findByName(genderDto.getName());

        if (currentGender == null) {
            Gender newGender = Gender
                    .builder()
                    .name(genderDto.getName())
                    .build();
            return genderRepository.save(newGender);
        }
        currentGender.setName(genderDto.getName());
        return genderRepository.save(currentGender);
    }

    @Override
    public Gender updateGender(Long genderId, GenderDto genderDto) {
        Gender newGender = genderRepository.findById((long) genderId).orElseThrow();

        newGender.setName(genderDto.getName());
        return genderRepository.save(newGender);    }
}
