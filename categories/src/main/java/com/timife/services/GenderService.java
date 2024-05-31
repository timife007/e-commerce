package com.timife.services;

import com.timife.model.dtos.GenderDto;
import com.timife.model.entities.Gender;

import java.util.List;

public interface GenderService {

    List<GenderDto> getAllGenders();

    GenderDto createGender(GenderDto genderDto);

    GenderDto updateGender(int genderId, GenderDto genderDto);

}
