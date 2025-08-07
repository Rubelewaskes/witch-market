package com.witchshop.ordermanagement.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.witchshop.ordermanagement.entity.TaskMessage;
import com.witchshop.ordermanagement.service.CoordinatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OMSConsumer {
    private final CoordinatorService coordinatorService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics="tasks.artifact.completed")
    public void consumeArtifactTask(String messageJson) {
        try {
            TaskMessage message = objectMapper.readValue(messageJson, TaskMessage.class);

            log.info("Прибыл результат {} этапа заказа {}", message.getStepNumber(), message.getOrderId());
            coordinatorService.taskResult(message);
        } catch (JsonProcessingException e) {
            log.error("Ошибка парсинга сообщения: {}", messageJson, e);
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные в сообщении: {}", messageJson, e);
        }
    }
}
