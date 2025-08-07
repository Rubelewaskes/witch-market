package com.witchshop.ordermanagement.service;

import com.witchshop.ordermanagement.entity.NewOrder;
import com.witchshop.ordermanagement.entity.OrderCreationResult;
import com.witchshop.ordermanagement.entity.TaskMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoordinatorService {
    private final KafkaService kafkaService;
    private final DBService dbService;
    private final TaskService taskService;

    @Transactional
    public void createNewOrder(NewOrder newOrder) {
        OrderCreationResult info = dbService.createNewOrder(newOrder);

        TaskMessage taskMessage = taskService.newOrderTask(info);

        kafkaService.newOrder(taskMessage);

        kafkaService.newArtifact(taskMessage);
    }

    @Transactional
    public void taskResult(TaskMessage taskMessage) {
        if (taskMessage == null || taskMessage.getPayload() == null) {
            log.error("Received null task message or payload");
            return;
            //TODO Подумать над Exception
        }
        Optional<Integer> next_step = dbService.getNextStep(taskMessage.getPipelineId(),taskMessage.getStepNumber()+1);

        if (next_step.isEmpty()){
            List< TaskMessage.TaskResult> results = taskMessage.getPayload().getPreviousResults();
            if (results.get(results.size() - 1).getStatus().equals("SUCCESS")){
                kafkaService.completed(taskMessage);
            }
            else{
                kafkaService.cancelled(taskMessage);
            }
        }
        else{
            /*
            * Тут идёт в бд, ищет новый шаг и отправляет дальше
            * */
        }
    }
}

