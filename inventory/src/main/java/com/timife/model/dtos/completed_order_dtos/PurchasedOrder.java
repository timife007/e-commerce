package com.timife.model.dtos.completed_order_dtos;

import com.timife.model.dtos.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchasedOrder {
    OrderStatus orderStatus;
    List<PurchasedOrderItemDto> purchasedOrderItemDtoList;
}
