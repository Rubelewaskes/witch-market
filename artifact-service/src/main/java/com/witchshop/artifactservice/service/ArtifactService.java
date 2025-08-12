package com.witchshop.artifactservice.service;

import com.witchshop.artifactservice.entity.Product;
import com.witchshop.artifactservice.kafka.ArtifactProducer;
import com.witchshop.artifactservice.mapper.TaskExecutionMapper;
import com.witchshop.artifactservice.mapper.WorkerMapper;
import com.witchshop.sharedlib.dao.Workers;
import com.witchshop.sharedlib.entity.TaskMessage;
import com.witchshop.sharedlib.enums.Specialization;
import com.witchshop.sharedlib.enums.TaskStatuses;
import com.witchshop.sharedlib.enums.WorkerStatuses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtifactService {

    private final Random random = new Random();
    private final ArtifactProducer artifactProducer;
    private final DBService dbService;

    public void processTask(TaskMessage task) {
        UUID taskId = task.getCorrelationId();

        Workers worker = dbService.selectWorker();
        dbService.updateWorkerInTask(worker.getId(), taskId);
        dbService.updateTaskStatus(taskId, TaskStatuses.IN_PROGRESS);

        Product createdArtifact = createProduct(task);
        TaskMessage resultMessage;

        if (createdArtifact.getCreatedSuccessfully()) {
            TaskMessage.TaskResult taskResult = createSuccessResponse(task, createdArtifact);
            resultMessage = creteResultMessage(taskResult, task);

            dbService.updateTaskAfterEnd(taskId, TaskStatuses.COMPLETED, taskResult.toText());
        }
        else{
            TaskMessage.TaskResult taskResult = createFailureResponse(task, createdArtifact);
            resultMessage = creteResultMessage(taskResult, task);

            dbService.updateTaskAfterEnd(taskId, TaskStatuses.FAILED, taskResult.toText());
        }

        artifactProducer.sendResult(resultMessage);
    }

    private Product createProduct(TaskMessage task) {
        log.info("Начали ковку артефакта");

        int delay = 5000 + random.nextInt(5000);
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean success = random.nextDouble() < 0.6;

        if (success) {
            Map<String, Object> condition = generateArtifactCondition();
            log.info("quality: {}, durability: {}", condition.get("quality"), condition.get("durability"));
            return new Product(
                    (String)condition.get("quality"),
                    (Integer)condition.get("durability")
            );
        } else {
            return new Product(
                    "Почему-то не вышло"
            );
        }
    }

    private TaskMessage.TaskResult createSuccessResponse(TaskMessage task, Product product) {


        TaskMessage.TaskResult taskResult = new TaskMessage.TaskResult();
        taskResult.setStepNumber(task.getStepNumber());
        taskResult.setStatus("SUCCESS");
        taskResult.setErrorMessage(null);
        taskResult.setDetails(Map.of("quality", product.getQuality(), "durability", product.getDurability()));

        return taskResult;
    }

    private TaskMessage.TaskResult createFailureResponse(TaskMessage task, Product product) {
        TaskMessage.TaskResult taskResult = new TaskMessage.TaskResult();
        taskResult.setStepNumber(task.getStepNumber());
        taskResult.setStatus("FAILED");
        taskResult.setErrorMessage(product.getErrorMessage());

        return taskResult;
    }

    private Map<String, Object> generateArtifactCondition() {
        String[] qualities = {"COMMON", "UNCOMMON", "RARE", "EPIC", "LEGENDARY"};
        String quality = qualities[random.nextInt(qualities.length)];

        return Map.of(
                "quality", quality,
                "durability", 80 + random.nextInt(20)
        );
    }

    private TaskMessage creteResultMessage(TaskMessage.TaskResult taskResult, TaskMessage task) {
        List<TaskMessage.TaskResult> previousResults = task.getPayload().getPreviousResults();
        if (previousResults == null) {
            previousResults = new ArrayList<>();
        }
        previousResults.add(taskResult);

        TaskMessage.Payload payload = new TaskMessage.Payload();
        payload.setIngredients(Collections.emptyList());
        payload.setRequirements(Collections.emptyList());
        payload.setPreviousResults(List.of(taskResult));

        return new TaskMessage(
                task.getOrderId(),
                task.getPipelineId(),
                task.getStepNumber(),
                task.getTaskType(),
                task.getSpecialization(),
                payload,
                LocalDateTime.now(),
                task.getCorrelationId()
        );
    }
}