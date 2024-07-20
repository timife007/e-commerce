package com.timife.services.impl;

import com.timife.model.OrderResponse;
import com.timife.model.OrderStatus;
import com.timife.repositories.PaymentRepository;
import com.timife.services.PaymentStatusPublishService;
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
    PaymentStatusPublishService paymentStatusPublishService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void makePayment(OrderResponse orderResponse) {
        try {
            //Make payment here
            orderResponse.setOrderStatus(OrderStatus.ORDER_SUCCESSFUL);
            //publish payment status with the orderResponse as successful.
            paymentStatusPublishService.publish(orderResponse);
            return;
        } catch (Exception exception) {
            orderResponse.setOrderStatus(OrderStatus.ORDER_FAILED);
            paymentStatusPublishService.publish(orderResponse);
            log.debug(exception.getLocalizedMessage());
        }
    }
}
