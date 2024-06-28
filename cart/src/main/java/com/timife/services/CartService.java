package com.timife.services;

import com.timife.model.dtos.OrderItemDto;
import com.timife.model.dtos.UpdateOrderItemDto;
import com.timife.model.entities.Cart;
import com.timife.model.entities.OrderItem;

public interface CartService {
    public Cart selectOrder(OrderItemDto orderItemDto);

    public Cart findCartById(Long userId);

    public Cart updateOrder(UpdateOrderItemDto updateOrderItemDto);
}
