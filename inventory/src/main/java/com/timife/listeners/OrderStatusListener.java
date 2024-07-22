package com.timife.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * This should be listening from the cart service, if successful or failed after payment.
 * To avoid having to send unnecessary information to the payment service, so return payment
 * status to cart service, and update the inventory service accordingly.
 */
@Component
@Profile("production")
@RequiredArgsConstructor
@Slf4j
public class OrderStatusListener {
    private final ObjectMapper objectMapper;

    @KafkaListener(id = "placedOrder", topics = "placedOrder.published")
    public String listens(final String updatedOrder) {
        try {
            //Update inventory db of successful order placed and do as expected.
            log.info("Received Status: {}", updatedOrder);
        } catch (Exception exception) {
            log.error("Invalid validation: {}", exception.getLocalizedMessage());
            throw new IllegalArgumentException(exception.getLocalizedMessage());
        }
        return updatedOrder;
    }
}