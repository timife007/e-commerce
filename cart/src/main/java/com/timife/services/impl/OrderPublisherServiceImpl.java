package com.timife.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timife.config.KafkaConfigProps;
import com.timife.model.dtos.PurchasedOrderItemDto;
import com.timife.model.responses.OrderResponse;
import com.timife.services.OrderPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderPublisherServiceImpl implements OrderPublisherService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaConfigProps kafkaConfigProps;
    @Override
    public void publishOrder(OrderResponse order) {
        try {
            final String payload = objectMapper.writeValueAsString(order);
            kafkaTemplate.send(kafkaConfigProps.getTopic(), payload);
            log.info("PUBLISHED: " + order.toString());
        } catch (Exception e) {
            throw new RuntimeException("Unable to publish order");
        }
    }

    @Override
    public void publishCompletedOrder(List<PurchasedOrderItemDto> purchasedItems) {
        try {
            final String payload = objectMapper.writeValueAsString(purchasedItems);
            kafkaTemplate.send("placedOrder.published", payload);
            log.info("PLACED_ORDER PUBLISHED: " + payload);
        } catch (Exception e) {
            throw new RuntimeException("Unable to publish order");
        }
    }


}
