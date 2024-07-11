package com.timife.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timife.config.KafkaConfigProps;
import com.timife.model.entities.Order;
import com.timife.services.OrderPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderPublisherServiceImpl implements OrderPublisherService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaConfigProps kafkaConfigProps;
    @Override
    public void publish(Order order) {
        try {
            final String payload = objectMapper.writeValueAsString(order);
            kafkaTemplate.send(kafkaConfigProps.getTopic(), payload);
            log.info("PUBLISHED: " + order.toString());
        } catch (Exception e) {
            throw new RuntimeException("Unable to publish token");
        }
    }
}
