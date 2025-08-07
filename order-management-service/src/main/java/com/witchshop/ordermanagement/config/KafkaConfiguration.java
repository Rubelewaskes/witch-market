package com.witchshop.ordermanagement.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic newOrderTopic() {
        return new NewTopic("tasks.artifact.pending", 3, (short) 1);
    }
    @Bean
    public NewTopic completedTopic() {
        return new NewTopic("tasks.artifact.completed", 3, (short) 1);
    }
    @Bean
    public NewTopic cancelledTopic() {
        return new NewTopic("tasks.artifact.completed", 3, (short) 1);
    }
}

