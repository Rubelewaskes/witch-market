package com.witchshop.ordermanagement.kafka;

import com.witchshop.sharedlib.entity.TaskMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OMSProducer {
    private final KafkaTemplate<String, TaskMessage> kafkaTemplate;
    private final TaskTopicFactory topicFactory;

    public void sendToNew (TaskMessage taskMessage) {
        String topic = "orders.new";
        kafkaTemplate.send(topic, taskMessage);
        log.info("Новый заказ {} отправлен в топик {}", taskMessage.getOrderId(), topic);
    }
    public void sendToCompleted (TaskMessage taskMessage) {
        String topic = "orders.completed";
        kafkaTemplate.send(topic, taskMessage);
        log.info("Заказ {} выполнен и отправлен в топик {}", taskMessage.getOrderId(), topic);
    }
    public void sendToCancelled (TaskMessage taskMessage) {
        String topic = "orders.cancelled";
        kafkaTemplate.send(topic, taskMessage);
        log.info("Заказ {}  отправлен в топик {}", taskMessage.getOrderId(), topic);
    }
    public void sendTask (TaskMessage taskMessage) {
        String topic = topicFactory.getTopic(taskMessage.getSpecialization());
        kafkaTemplate.send(topic, taskMessage);
        log.debug("Задача {} заказа {} отправлена в топик {}", taskMessage.getStepNumber(), taskMessage.getOrderId(), topic);
    }
}
