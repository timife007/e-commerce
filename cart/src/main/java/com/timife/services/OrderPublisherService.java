package com.timife.services;

import com.timife.model.dtos.completed_order_dtos.PurchasedOrder;
import com.timife.model.dtos.completed_order_dtos.PurchasedOrderItemDto;
import com.timife.model.responses.OrderResponse;

import java.util.List;

public interface OrderPublisherService {

    void publishOrder(OrderResponse order);

    void publishCompletedOrder(PurchasedOrder purchasedOrder);
}
