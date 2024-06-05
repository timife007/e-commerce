package com.timife.model.responses;

import com.timife.model.entities.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private Long genderId;
    private List<Section> sectionList;
}
