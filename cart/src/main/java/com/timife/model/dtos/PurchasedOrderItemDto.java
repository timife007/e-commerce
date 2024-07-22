package com.timife.model.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchasedOrderItemDto {

    private Long id;
    private Long productSizeId;
    private Long productId;
    private Long sizeId;
    private Integer qty;
    private Long cartId;
}
