package com.timife.services.impl;

import com.timife.model.GenderCategory;
import com.timife.model.dtos.GenderDto;
import com.timife.repositories.GenderRepository;
import com.timife.services.CategoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final GenderRepository genderRepository;

    @Override
    public GenderCategory createUpdateCategory(GenderDto genderDto) {

        GenderCategory currentCategory = genderRepository.findByName(genderDto.getName());

        if (currentCategory == null) {
            GenderCategory newGender = GenderCategory
                    .builder()
                    .name(genderDto.getName())
                    .build();
            return genderRepository.save(newGender);
        }
        currentCategory.setName(genderDto.getName());
        return genderRepository.save(currentCategory);
    }
}
