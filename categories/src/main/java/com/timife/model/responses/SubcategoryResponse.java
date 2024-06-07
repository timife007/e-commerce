package com.timife.model.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SubcategoryResponse {

    private Long id;
    private String name;
    private Long categoryId;

}
