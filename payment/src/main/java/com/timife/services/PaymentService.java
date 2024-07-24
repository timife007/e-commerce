package com.timife.services;

import com.timife.model.OrderDto;

public interface PaymentService {

    void makePayment(OrderDto orderDto);
}
