package com.witchshop.sharedlib.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.witchshop.sharedlib.enums.Specialization;

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
    private LocalDateTime timestamp;
    private UUID correlationId;

    @Data
    public static class Payload {
        private List<Map<String, Object>> ingredients;
        private List<Map<String, Object>> requirements;
        private List<TaskResult> previousResults;
    }
    @Data
    public static class TaskResult {
        private Integer stepNumber;
        private String status;
        private String errorMessage;
        private Map<String, Object> details;

        private static final ObjectMapper mapper = new ObjectMapper();

        public String toText() {
            try {
                return mapper.writeValueAsString(this);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error serializing TaskResult to JSON", e);
            }
        }
    }
}

