package com.timife.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timife.model.responses.OrderResponse;
import com.timife.repositories.OrderRepository;
import com.timife.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
@RequiredArgsConstructor
@Slf4j
public class PaymentStatusListener {
    private final ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;


    private final CartService cartService;

    @KafkaListener(id = "payment", topics = "payment.published")
    public String listens(final String paymentOrder) {
        log.info("Received Status: {}", paymentOrder);
        try {
            OrderResponse orderResponse = objectMapper.readValue(paymentOrder, OrderResponse.class);
            //Update order db of status and do as expected and also update the inventory db accordingly.
            log.error("Before publishing: {}",orderResponse.toString());

            cartService.updateInventory(orderResponse);
        } catch (Exception exception) {
            log.error("Invalid validation: {}", exception.getLocalizedMessage());
            throw new IllegalArgumentException(exception.getLocalizedMessage());
        }
        return paymentOrder;
    }

}