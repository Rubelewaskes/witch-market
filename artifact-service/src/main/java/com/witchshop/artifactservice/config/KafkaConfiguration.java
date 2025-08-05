package com.witchshop.artifactservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic pendingTopic() {
        return new NewTopic("tasks.artifact.pending", 3, (short) 1);
    }
    @Bean
    public NewTopic completeTopic() {
        return new NewTopic("tasks.artifact.completed", 3, (short) 1);
    }
}
