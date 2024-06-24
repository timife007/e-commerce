package com.timife.model.responses;

import com.timife.model.entities.Brand;
import com.timife.model.entities.Image;
import com.timife.model.entities.ProductSize;
import com.timife.model.entities.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private List<Image> imageList;
    private Double salePrice;
    private Double originalPrice;
    private String productCode;
    private List<Size> sizes;
    private String productDetails;
    private Brand brand;
    private String colour;
    private Long categoryId;
    private String lookAfterMe;
    private List<ProductSize> productSizes;
}
