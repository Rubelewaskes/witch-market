package com.witchshop.artifactservice.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.witchshop.artifactservice.enums.Specialization;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskMessage {
    private UUID orderId;
    private BigInteger pipelineId;
    private BigInteger stepId;
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
        private BigInteger stepId;
        private String status;
        private String errorMessage;
        private Map<String, Object> details;
    }
}
