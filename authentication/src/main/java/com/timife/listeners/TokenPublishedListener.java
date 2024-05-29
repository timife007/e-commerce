package com.timife.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timife.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Profile("production")
@RequiredArgsConstructor
@Slf4j
public class TokenPublishedListener {
    private final ObjectMapper objectMapper;

    private final AuthenticationService authenticationService;

//    private final NotificationService notificationService;

    @KafkaListener(topics = "token.published")
    public String listens(final String token) {
        log.info("Received Token: {}", token);

        try {
//            final Map<String, Object> payload = readJsonAsMap(token);
            log.error("I don't know sha");
            authenticationService.validateToken(token);
        } catch (Exception exception) {
            log.error("Invalid validation: {}", exception.getLocalizedMessage());
        }
        return token;
    }

    private Map<String, Object> readJsonAsMap(final String json) {
        try {
            final TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
            };
            return objectMapper.readValue(json, typeRef);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }


}
