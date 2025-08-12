package com.witchshop.sharedlib.dao;

import lombok.Data;

@Data
public class PipelineDefinition {
    private Long id;
    private String name;
    private Boolean requiresHandoff;
}