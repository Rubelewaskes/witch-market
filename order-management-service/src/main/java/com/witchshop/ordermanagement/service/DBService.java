package com.witchshop.ordermanagement.service;

import com.witchshop.ordermanagement.entity.NewOrder;
import com.witchshop.ordermanagement.entity.OrderStatus;
import com.witchshop.ordermanagement.entity.TaskStatus;
import com.witchshop.ordermanagement.mapper.WorkerMapper;
import com.witchshop.sharedlib.dao.Order;
import com.witchshop.sharedlib.dao.PipelineStepDefinition;
import com.witchshop.sharedlib.dao.TaskExecution;
import com.witchshop.sharedlib.dao.Workers;
import com.witchshop.sharedlib.enums.OrderStatuses;
import com.witchshop.sharedlib.enums.Specialization;
import com.witchshop.sharedlib.enums.TaskStatuses;
import com.witchshop.ordermanagement.mapper.OrderMapper;
import com.witchshop.ordermanagement.mapper.PipelineStepDefinitionMapper;
import com.witchshop.ordermanagement.mapper.TaskExecutionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DBService {
    private final OrderMapper orderMapper;
    private final PipelineStepDefinitionMapper pipelineStepDefinitionMapper;
    private final TaskExecutionMapper taskExecutionMapper;
    private final WorkerMapper workerMapper;

    public Order insertNewOrder(NewOrder newOrder) {
        Order order = new Order();
        order.setPipelineId(newOrder.getPipelineId());
        order.setClientId(newOrder.getClientId());
        order.setStatus(OrderStatuses.IN_PROGRESS);

        orderMapper.insertOrder(order);
        return order;
    }
    public OrderStatus getOrderStatusById(UUID id) {
        return orderMapper.getOrderStatusById(id);
    }

    public void updateOrderStatus(UUID orderId, OrderStatuses orderStatus) {
        orderMapper.updateOrderStatus(orderId, orderStatus);
    }
    public void markOrderAsCompleted(UUID orderId){
        orderMapper.markOrderAsCompleted(orderId);
    }

    public PipelineStepDefinition getStepByPipelineIdAndStepNumber(Long pipelineId, Integer stepNumber) {
        return pipelineStepDefinitionMapper.selectStepByPipelineIdAndStepNumber(pipelineId, stepNumber);
    }

    public TaskExecution insertNewTask(UUID orderId, Integer StepNumber, Specialization specialization, TaskStatuses taskStatus) {
        TaskExecution task = new TaskExecution();
        task.setOrderId(orderId);
        task.setStepNumber(StepNumber);
        task.setSpecialization(specialization);
        task.setStatus(taskStatus);

        taskExecutionMapper.insertTask(task);
        return task;
    }

    public PipelineStepDefinition getNextStep(Long pipelineId, Integer stepNumber){
        return pipelineStepDefinitionMapper.selectStepByPipelineIdAndStepNumber(pipelineId, stepNumber);
    }

    public TaskStatus getTaskStatusById(UUID id){
        return taskExecutionMapper.getTaskStatusById(id);
    }

    public List<Workers> getWorkersByType(Specialization type) {
        return workerMapper.getWorkersBySpecialization(type);
    }
}
