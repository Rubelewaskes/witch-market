package com.witchshop.ordermanagement.kafka;

import com.witchshop.sharedlib.enums.Specialization;
import org.springframework.stereotype.Component;

@Component
public class TaskTopicFactory {
    public String getTopic(Specialization specialization) {
        return "tasks." + specialization.name().toLowerCase() + ".pending";
    }
}
