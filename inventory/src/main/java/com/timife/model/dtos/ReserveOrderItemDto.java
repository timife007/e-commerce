package com.timife.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveOrderItemDto {

    private Long productSizeId;
    private Integer qty;
}
