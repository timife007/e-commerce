package com.timife.services;

import com.timife.model.OrderDto;

public interface PaymentStatusPublishService {

    void publish(OrderDto order);
}
