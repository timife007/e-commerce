package com.timife.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderItemDto {
    private Long userId;
    private Long productId;
    private Long sizeId;
    private Integer qty;
}
