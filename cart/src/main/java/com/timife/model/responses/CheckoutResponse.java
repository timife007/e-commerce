package com.timife.model.responses;

import com.timife.model.dtos.DeliveryAddressDto;
import com.timife.model.entities.Cart;
import com.timife.model.entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CheckoutResponse {
    private Long userId;
    private Double subTotal;
    private List<OrderItem> orderItems;
    private Double deliveryFee;
    private Double sumTotal;
    private List<DeliveryAddressDto> deliveryAddressDto;
}
