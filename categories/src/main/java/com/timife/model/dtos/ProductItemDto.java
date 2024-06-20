package com.timife.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemDto {

    private String name;
    private String price;
    private String size;
    private String frontImage;
    private String details;
    private String sustainability;
    private String careGuide;
    private String shippingAndReturns;
    private String tag;
    private List<String> images;
    private String advert;
    private Long categoryId;
    private Long subCategoryId;
    private Long GenderId;
}
