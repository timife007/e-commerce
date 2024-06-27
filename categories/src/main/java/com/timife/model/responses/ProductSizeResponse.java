package com.timife.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductSizeResponse {
    private Long id;
    private Integer qtyInStock;
    private Long sizeId;
    private Double price;
}
