package com.timife.services;

import com.timife.model.dtos.OrderItemDto;
import com.timife.model.dtos.UpdateOrderItemDto;
import com.timife.model.entities.Cart;
import com.timife.model.entities.OrderItem;
import com.timife.model.responses.CheckoutResponse;

public interface CartService {
    public Cart selectOrder(OrderItemDto orderItemDto);

    public Cart findCartById(Long userId);

    public Cart updateOrder(UpdateOrderItemDto updateOrderItemDto);

    public String setDeliveryFeeBasedOn(String address);

    public CheckoutResponse checkout(Integer userId);

    public String confirmOrder(Long userId);
}
