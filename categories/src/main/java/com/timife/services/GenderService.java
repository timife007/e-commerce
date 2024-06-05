package com.timife.services;

import com.timife.model.dtos.GenderDto;
import com.timife.model.entities.Gender;
import com.timife.model.responses.GenderResponse;

import java.util.List;

public interface GenderService {

    List<GenderResponse> getAllGenders();

    GenderResponse createGender(GenderDto genderDto);

    GenderResponse updateGender(Long genderId, GenderDto genderDto);

}
