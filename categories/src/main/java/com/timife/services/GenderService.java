package com.timife.services;

import com.timife.model.dtos.GenderDto;
import com.timife.model.entities.Gender;

import java.util.List;

public interface GenderService {

    List<Gender> getAllGenders();

    Gender createUpdateGender(GenderDto genderDto);

    Gender updateGender(Long genderId, GenderDto genderDto);

}
