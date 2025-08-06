package com.witchshop.ordermanagement.kafka;

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

    @KafkaListener(topics="tasks.artifact.completed")
    public void consumeArtifactTask(TaskMessage message) {
        log.info("Прибыл результат {} этапа заказа {}", message.getStepId(), message.getOrderId());
        coordinatorService.taskResult(message);
    }
}
