package com.timife.services.impl;

import com.timife.model.OrderResponse;
import com.timife.services.PaymentService;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymentApproved(OrderResponse orderResponse) {
        return orderResponse.getOrderStatus().name();
    }
}
