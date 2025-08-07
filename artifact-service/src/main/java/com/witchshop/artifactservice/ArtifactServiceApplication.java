package com.witchshop.artifactservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ArtifactServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArtifactServiceApplication.class, args);
    }
}
