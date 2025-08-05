package com.witchshop.artifactservice.kafka;

import com.witchshop.artifactservice.entity.TaskMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor

public class ArtifactProducer {
    private final KafkaTemplate<String, TaskMessage> kafkaTemplate;

    public void sendResult(TaskMessage result) {
        String topic = "tasks.artifact.completed";
        kafkaTemplate.send(topic, result);
        log.info("Отправлен результат задачи {} в топик {}", result, topic);
    }
}