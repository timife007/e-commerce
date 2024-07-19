package com.timife.services;

import com.timife.model.OrderResponse;

public interface PaymentService {

    String makePayment(OrderResponse orderResponse);
}
