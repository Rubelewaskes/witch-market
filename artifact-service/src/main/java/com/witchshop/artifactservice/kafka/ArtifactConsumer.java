package com.witchshop.artifactservice.kafka;

import com.witchshop.artifactservice.kafka.ArtifactProducer;
import com.witchshop.artifactservice.entity.TaskMessage;
import com.witchshop.artifactservice.service.ArtifactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArtifactConsumer {

    private final ArtifactService processingService;
    private final ArtifactProducer resultProducer;

    @KafkaListener(topics = "tasks.artifact.pending")
    public void consumeTask(TaskMessage task) {
        log.info("Получена новая задача: {}", task);

        TaskMessage result = processingService.processTask(task);

        resultProducer.sendResult(result);
    }
}
