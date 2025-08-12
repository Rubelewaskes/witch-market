package com.witchshop.ordermanagement.service;

import com.witchshop.ordermanagement.kafka.OMSProducer;
import com.witchshop.sharedlib.entity.TaskMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {
    private final OMSProducer omsProducer;

    public void newOrder(TaskMessage  taskMessage) {omsProducer.sendToNew(taskMessage);}
    public void completed (TaskMessage taskMessage) {omsProducer.sendToCompleted(taskMessage);}
    public void cancelled (TaskMessage taskMessage) {omsProducer.sendToCancelled(taskMessage);}
    public void sendTask (TaskMessage taskMessage) {omsProducer.sendTask(taskMessage);
    }
}
