package com.timife.model.dtos;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveOrderItemDto {
    
    private Long productSizeId;
    private Integer qty;
}