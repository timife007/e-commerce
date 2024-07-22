package com.timife.services;

import com.timife.model.OrderResponse;

public interface PaymentStatusPublishService {

    void publish(OrderResponse order);
}
