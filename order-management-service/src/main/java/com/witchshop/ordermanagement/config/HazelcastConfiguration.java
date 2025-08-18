package com.witchshop.ordermanagement.config;

import com.hazelcast.config.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class HazelcastConfiguration {
    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();

        config.setInstanceName("witchshop-hazelcast-instance");

        config.addMapConfig(new MapConfig()
                .setName("pipeline_step_definitions")
                .setTimeToLiveSeconds(600)
        );

        return config;
    }
}
