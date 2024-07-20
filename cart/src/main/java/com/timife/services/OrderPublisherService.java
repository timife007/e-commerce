package com.timife.services;

import com.timife.model.entities.Order;
import com.timife.model.entities.OrderItem;
import com.timife.model.responses.OrderResponse;

import java.util.List;

public interface OrderPublisherService {

    void publishOrder(OrderResponse order);

    void publishCompletedOrder(List<OrderItem> orderItems);
}
