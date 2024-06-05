package com.timife.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryDto {
//    Long id;
    String name;
    Long genderId;
//    List<SectionDto> sectionDtoList;
}
