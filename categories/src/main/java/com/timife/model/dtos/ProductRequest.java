package com.timife.model.dtos;

import com.timife.model.entities.Brand;
import com.timife.model.entities.Colour;
import com.timife.model.entities.Image;
import com.timife.model.entities.ProductSize;
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
    private Colour colour;
    private List<Image> images;
    private Brand brand;
    private String productCode;
}
