package com.timife.model.responses;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CategoryListResponse {
    private Long id;
    private String name;
    private Long genderId;
    private Long parentId;
}
