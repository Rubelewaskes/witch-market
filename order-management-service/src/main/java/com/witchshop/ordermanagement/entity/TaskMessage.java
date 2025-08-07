package com.witchshop.ordermanagement.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.witchshop.ordermanagement.enums.Specialization;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskMessage {
    private UUID orderId;
    private Long pipelineId;
    private Integer stepNumber;
    private String taskType;
    private Specialization specialization;
    private Payload payload;
    private String timestamp;
    private UUID correlationId;

    @Data
    public static class Payload {
        private List<String> ingredients;
        private Map<String, Object> requirements;
        private List<TaskResult> previousResults;
    }
    @Data
    public static class TaskResult {
        private Integer stepNumber;
        private String status;
        private String errorMessage;
        private Map<String, Object> details;
    }
}
