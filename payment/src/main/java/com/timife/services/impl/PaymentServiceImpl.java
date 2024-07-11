package com.timife.services.impl;

import com.timife.model.OrderResponse;
import com.timife.repositories.PaymentRepository;
import com.timife.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public String paymentApproved(OrderResponse orderResponse) {
        return orderResponse.getOrderStatus().name();
    }
}
