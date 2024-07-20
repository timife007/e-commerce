package com.timife.services.kafka.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timife.config.KafkaConfigProps;
import com.timife.services.kafka.TokenPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenPublisherServiceImpl implements TokenPublisherService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaConfigProps kafkaConfigProps;

    @Override
    public void publish(String token) {
        try {
            
//            final String payload = objectMapper.writeValueAsString(token);
            kafkaTemplate.send(kafkaConfigProps.getTopic(), token);
            log.info("PUBLISHED: " + token);
        } catch (Exception e) {
            throw new RuntimeException("Unable to publish token");
        }
    }
}
