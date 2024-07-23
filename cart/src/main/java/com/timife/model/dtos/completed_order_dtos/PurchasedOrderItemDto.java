package com.timife.model.dtos.completed_order_dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * data object of each order item that has been completed, either failed or successful.
 */
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
