package com.witchshop.sharedlib.dao;

import com.witchshop.sharedlib.enums.Specialization;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PipelineStepDefinition {
    private Integer id;
    private Long pipelineId;
    private Integer stepNumber;
    private Specialization specialization;
    private String taskType;
    private List<Map<String, Object>> ingredients;
    private List<Map<String, Object>> requirements;
}
