package com.witchshop.artifactservice.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.witchshop.artifactservice.enums.Specialization;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskMessage {
    private UUID orderId;
    private Integer pipelineId;
    private Integer stepId;
    private String taskType;
    private Specialization specialization; // enum: ARTIFACTOR, SCHOLAR и т.д.
    private Payload payload;
    private String timestamp;
    private UUID correlationId;

    @Data
    public static class Payload {
        private List<String> ingredients;
        private Map<String, Object> requirements;
        private Map<String, Object> previousResults;
    }
}