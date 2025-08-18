package com.witchshop.sharedlib.dao;

import lombok.Data;

import java.io.Serializable;

@Data
public class PipelineDefinition implements Serializable {
    private Long id;
    private String name;
    private Boolean requiresHandoff;
}