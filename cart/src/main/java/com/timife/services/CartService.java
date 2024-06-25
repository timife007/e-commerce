package com.timife.services;

import com.timife.model.dtos.OrderItemDto;

public interface CartService {
    public String selectOrder(OrderItemDto orderItemDto);
}
