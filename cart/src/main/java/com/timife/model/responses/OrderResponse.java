package com.timife.model.responses;


import com.timife.model.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private Long userId;
    private Double subTotal;
    private Double deliveryFee;
    private Double sumTotal = 0.0;
    private OrderStatus orderStatus;
}
