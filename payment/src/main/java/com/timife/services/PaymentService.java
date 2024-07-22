package com.timife.services;

import com.timife.model.OrderResponse;

public interface PaymentService {

    void makePayment(OrderResponse orderResponse);
}
