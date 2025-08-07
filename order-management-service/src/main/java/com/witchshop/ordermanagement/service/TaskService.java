package com.witchshop.ordermanagement.service;

import com.witchshop.ordermanagement.entity.OrderCreationResult;
import com.witchshop.ordermanagement.entity.TaskMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class TaskService {
    public TaskMessage newOrderTask(OrderCreationResult info) {
        TaskMessage.Payload payload = new TaskMessage.Payload();
        payload.setIngredients(info.getIngredients());
        payload.setRequirements(info.getRequirements());

        return new TaskMessage(
                info.getOrderId(),
                info.getPipelineId(),
                0,
                info.getTaskType(),
                info.getSpecialization(),
                payload,
                "2025-08-05T12:00:00Z",
                UUID.fromString("f1e2d3c4-5678-9012-3456-7890abcdef12")
        );
    }
}
