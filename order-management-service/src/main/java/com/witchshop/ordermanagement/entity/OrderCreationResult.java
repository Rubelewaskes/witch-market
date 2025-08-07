package com.witchshop.ordermanagement.entity;

import com.witchshop.ordermanagement.enums.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationResult {
    private UUID orderId;
    private Long pipelineId;
    private String taskType;
    private Specialization specialization;
    private List<String> ingredients;
    private Map<String, Object> requirements;
}
