package com.witchshop.artifactservice.service;

import com.witchshop.artifactservice.entity.TaskMessage;
import com.witchshop.artifactservice.kafka.ArtifactProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtifactService {

    private final Random random = new Random();
    private final ArtifactProducer resultProducer;

    public void processTask(TaskMessage task) {
        log.info("Начали ковку артефакта");

        int delay = 5000 + random.nextInt(5000);
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean success = random.nextDouble() < 0.6;

        if (success) {
            createSuccessResponse(task);
        } else {
            createFailureResponse(task);
        }
    }

    private void createSuccessResponse(TaskMessage task) {
        Map<String, Object> result = generateArtifactResult();
        log.info("quality: {}, durability: {}", result.get("quality"), result.get("durability"));

        TaskMessage.TaskResult taskResult = new TaskMessage.TaskResult();
        taskResult.setStepId(task.getStepId());
        taskResult.setStatus("SUCCESS");
        taskResult.setErrorMessage(null);
        taskResult.setDetails(result);

        TaskMessage resultMessage = creteResultMessage(taskResult, task);

        resultProducer.sendResult(resultMessage);
    }

    private void createFailureResponse(TaskMessage task) {
        log.warn("Неудачная ковка артефакта");

        TaskMessage.TaskResult taskResult = new TaskMessage.TaskResult();
        taskResult.setStepId(task.getStepId());
        taskResult.setStatus("FAILED");
        taskResult.setErrorMessage("Какая-то причина");

        TaskMessage resultMessage = creteResultMessage(taskResult, task);

        resultProducer.sendResult(resultMessage);
    }

    private Map<String, Object> generateArtifactResult() {
        String[] qualities = {"COMMON", "UNCOMMON", "RARE", "EPIC", "LEGENDARY"};
        String quality = qualities[random.nextInt(qualities.length)];

        return Map.of(
                "quality", quality,
                "durability", 80 + random.nextInt(20)
        );
    }

    private TaskMessage creteResultMessage(TaskMessage.TaskResult taskResult, TaskMessage task) {
        TaskMessage.Payload payload = new TaskMessage.Payload();
        payload.setIngredients(Collections.emptyList());
        payload.setRequirements(Collections.emptyMap());
        payload.setPreviousResults(List.of(taskResult));

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
}