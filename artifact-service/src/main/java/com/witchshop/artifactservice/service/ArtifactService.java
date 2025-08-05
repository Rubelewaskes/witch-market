package com.witchshop.artifactservice.service;

import com.witchshop.artifactservice.entity.TaskMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ArtifactService {

    private final Random random = new Random();

    public TaskMessage processTask(TaskMessage task) {
        log.info("Начали ковку артефакта");

        int delay = 5000 + random.nextInt(5000);
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean success = random.nextDouble() < 0.6;

        if (success) {
            return createSuccessResponse(task);
        } else {
            return createFailureResponse(task);
        }
    }

    private TaskMessage createSuccessResponse(TaskMessage task) {
        Map<String, Object> result = generateArtifactResult();
        log.info("quality: {},durability: {}", result.get("quality"), result.get("durability"));

        TaskMessage.Payload payload = new TaskMessage.Payload();
        payload.setIngredients(Collections.emptyList());
        payload.setRequirements(Collections.emptyMap());
        payload.setPreviousResults(Map.of(
                "status", "SUCCESS",
                "quality", result.get("quality"),
                "durability", result.get("durability"),
                "message", result
        ));

        return new TaskMessage(
                task.getOrderId(),
                task.getPipelineId(),
                task.getStepId(),
                task.getTaskType(),
                task.getSpecialization(),
                payload,
                Instant.now().toString(),
                task.getCorrelationId()
        );
    }

    private TaskMessage createFailureResponse(TaskMessage task) {
        log.warn("Неудачная ковка артефакта");

        TaskMessage.Payload payload = new TaskMessage.Payload();
        payload.setIngredients(Collections.emptyList());
        payload.setRequirements(Collections.emptyMap());
        payload.setPreviousResults(Map.of(
                "status", "FAILED"
        ));

        return new TaskMessage(
                task.getOrderId(),
                task.getPipelineId(),
                task.getStepId(),
                task.getTaskType(),
                task.getSpecialization(),
                payload,
                Instant.now().toString(),
                task.getCorrelationId()
        );
    }

    private Map<String, Object> generateArtifactResult() {
        String[] qualities = {"COMMON", "UNCOMMON", "RARE", "EPIC", "LEGENDARY"};
        String quality = qualities[random.nextInt(qualities.length)];

        return Map.of(
                "quality", quality,
                "durability", 80 + random.nextInt(20)
        );
    }
}