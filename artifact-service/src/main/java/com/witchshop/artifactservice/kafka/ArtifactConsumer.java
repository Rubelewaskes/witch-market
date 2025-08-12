package com.witchshop.artifactservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.witchshop.artifactservice.service.ArtifactService;
import com.witchshop.sharedlib.entity.TaskMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArtifactConsumer {

    private final ArtifactService artifactService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "tasks.artifact.pending")
    public void consumeTask(String messageJson) {
        try {
            TaskMessage message = objectMapper.readValue(messageJson, TaskMessage.class);
            log.info("Получена новая задача заказа {}", message.getOrderId());
            artifactService.processTask(message);
        } catch (JsonProcessingException e) {
            log.error("Ошибка парсинга сообщения: {}", messageJson, e);
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные в сообщении: {}", messageJson, e);
        }
    }
}
