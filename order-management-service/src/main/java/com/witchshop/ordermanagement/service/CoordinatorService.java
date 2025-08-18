package com.witchshop.ordermanagement.service;

import com.witchshop.ordermanagement.entity.NewOrder;
import com.witchshop.ordermanagement.entity.OrderStatus;
import com.witchshop.ordermanagement.entity.TaskStatus;
import com.witchshop.sharedlib.dao.Order;
import com.witchshop.sharedlib.dao.PipelineStepDefinition;
import com.witchshop.sharedlib.dao.TaskExecution;
import com.witchshop.sharedlib.dao.Workers;
import com.witchshop.sharedlib.enums.OrderStatuses;
import com.witchshop.sharedlib.enums.Specialization;
import com.witchshop.sharedlib.enums.TaskStatuses;
import com.witchshop.sharedlib.entity.TaskMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoordinatorService {
    private final KafkaService kafkaService;
    private final DBService dbService;

    @Transactional
    public void createNewOrder(NewOrder newOrder) {
        Order createdOrder = dbService.insertNewOrder(newOrder);
        PipelineStepDefinition step = dbService.getStepByPipelineIdAndStepNumber(createdOrder.getPipelineId(), 0);

        TaskExecution task = dbService.insertNewTask(createdOrder.getId(),
                step.getStepNumber(),
                step.getSpecialization(),
                TaskStatuses.PENDING
        );

        TaskMessage.Payload payload = new TaskMessage.Payload();
        payload.setIngredients(step.getIngredients());
        payload.setRequirements(step.getRequirements());

        TaskMessage taskMessage = new TaskMessage(
                createdOrder.getId(),
                createdOrder.getPipelineId(),
                task.getStepNumber(),
                step.getTaskType(),
                task.getSpecialization(),
                payload,
                LocalDateTime.now(),
                task.getId()
        );

        kafkaService.newOrder(taskMessage);

        kafkaService.sendTask(taskMessage);
    }

    @Transactional
    public void taskCompleted(TaskMessage resultTaskMessage) {
        if (resultTaskMessage == null || resultTaskMessage.getPayload() == null) {
            log.error("Received null task message or payload");
            return;
            //TODO Подумать над Exception
        }
        PipelineStepDefinition nextStep = dbService.getStepByPipelineIdAndStepNumber(resultTaskMessage.getPipelineId(),resultTaskMessage.getStepNumber()+1);
        List<TaskMessage.TaskResult> results = resultTaskMessage.getPayload().getPreviousResults();

        if (nextStep == null){

            if (results.get(results.size() - 1).getStatus().equals("SUCCESS")){
                dbService.updateOrderStatus(resultTaskMessage.getOrderId(), OrderStatuses.COMPLETED);
                dbService.markOrderAsCompleted(resultTaskMessage.getOrderId());
                kafkaService.completed(resultTaskMessage);
            }
            else{
                //Потом будет ретрай логика, пока в завершённое летит
                dbService.updateOrderStatus(resultTaskMessage.getOrderId(), OrderStatuses.CANCELLED);
                dbService.markOrderAsCompleted(resultTaskMessage.getOrderId());
                kafkaService.cancelled(resultTaskMessage);
            }
        }
        else{
            if (results.get(results.size() - 1).getStatus().equals("FAILED")){
                //Если один из этапов был неудачным, то остальные не выполняются
                dbService.updateOrderStatus(resultTaskMessage.getOrderId(), OrderStatuses.CANCELLED);
                dbService.markOrderAsCompleted(resultTaskMessage.getOrderId());
                kafkaService.cancelled(resultTaskMessage);
            }
            else{
                dbService.updateOrderStatus(resultTaskMessage.getOrderId(), OrderStatuses.IN_PROGRESS);

                TaskExecution task = dbService.insertNewTask(
                        resultTaskMessage.getOrderId(),
                        nextStep.getStepNumber(),
                        nextStep.getSpecialization(),
                        TaskStatuses.PENDING
                );

                TaskMessage.Payload payload = new TaskMessage.Payload();
                payload.setIngredients(nextStep.getIngredients());
                payload.setRequirements(nextStep.getRequirements());

                TaskMessage taskMessage = new TaskMessage(
                        resultTaskMessage.getOrderId(),
                        resultTaskMessage.getPipelineId(),
                        task.getStepNumber(),
                        nextStep.getTaskType(),
                        task.getSpecialization(),
                        payload,
                        LocalDateTime.now(),
                        task.getId()
                );

                kafkaService.sendTask(taskMessage);
            }
        }
    }
    public OrderStatus getOrderById(String id) {
        UUID uuid = parseUuid(id);
        OrderStatus order = dbService.getOrderStatusById(uuid);
        requireNonNull(order, "Order not found: " + id);
        return order;
    }

    public List<Workers> getWorkersByType(String workerType) {
        Specialization workerTypeSpec;
        try {
            workerTypeSpec = Specialization.valueOf(workerType);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Worker Type format: " + workerType, e);
        }
        List<Workers> worker = dbService.getWorkersByType(workerTypeSpec);
        requireNonNull(worker, "There is no workers with specialization:" + workerTypeSpec);
        return worker;
    }

    public TaskStatus getTaskStatus(String id) {
        UUID uuid = parseUuid(id);
        TaskStatus task = dbService.getTaskStatusById(uuid);
        requireNonNull(task, "Task not found: " + id);
        return task;
    }

    private UUID parseUuid(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format: " + id, e);
        }
    }

    private <T> T requireNonNull(T obj, String message) {
        if (obj == null) {
            throw new NoSuchElementException(message);
        }
        return obj;
    }
}
