package com.timife.services;

import com.timife.model.entities.Order;

public interface OrderPublisherService {

    void publish(Order order);
}
