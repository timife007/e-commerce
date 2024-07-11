package com.timife.services;

import com.timife.model.entities.Order;
import com.timife.model.responses.OrderResponse;

public interface OrderPublisherService {

    void publish(OrderResponse order);
}
