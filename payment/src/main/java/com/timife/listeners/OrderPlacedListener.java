package com.timife.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timife.model.OrderDto;
import com.timife.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Order placed in cart listener to perform payment transaction
 */

@Component
@Profile("production")
@RequiredArgsConstructor
@Slf4j
public class OrderPlacedListener {
    private final ObjectMapper objectMapper;

    private final PaymentService paymentService;

    @KafkaListener(id = "order", topics = "order.published")
    public String listens(final String order) {
        log.info("Received Token: {}", order);
        log.error(order);
        try {
            OrderDto item = objectMapper.readValue(order, OrderDto.class);
            paymentService.makePayment(item);
        } catch (Exception exception) {
            log.error("Invalid validation: {}", exception.getLocalizedMessage());
            throw new IllegalArgumentException(exception.getLocalizedMessage());
        }
        return order;
    }

}
