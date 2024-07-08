package com.timife.model.dtos;

import com.timife.model.entities.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductRequest {
    private String productName;
    private Long categoryId;
    private List<ProductSizeRequest> productSizeList;
    private Double salePrice;
    private Double originalPrice;
    private String productDescription;
    private String colour;
    private List<ImageRequest> images;
    private Brand brand;
    private String lookAfterMe;
    private String productCode;
}
