package com.timife.model.responses;

import lombok.*;

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