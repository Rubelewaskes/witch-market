package com.witchshop.ordermanagement.service;

import com.witchshop.ordermanagement.entity.NewOrder;
import com.witchshop.sharedlib.dao.Order;
import com.witchshop.sharedlib.dao.PipelineStepDefinition;
import com.witchshop.sharedlib.dao.TaskExecution;
import com.witchshop.sharedlib.enums.OrderStatuses;
import com.witchshop.sharedlib.enums.TaskStatuses;
import com.witchshop.sharedlib.entity.TaskMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        PipelineStepDefinition nextStep = dbService.getNextStep(resultTaskMessage.getPipelineId(),resultTaskMessage.getStepNumber()+1);
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
}
