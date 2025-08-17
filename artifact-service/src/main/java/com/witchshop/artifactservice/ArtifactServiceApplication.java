package com.witchshop.artifactservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan({"com.witchshop.artifactservice.mapper",
             "com.witchshop.sharedlib.mapper"})
@EnableScheduling
public class ArtifactServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArtifactServiceApplication.class, args);
    }
}
