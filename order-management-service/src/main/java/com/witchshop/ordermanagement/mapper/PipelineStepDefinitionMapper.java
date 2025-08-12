package com.witchshop.ordermanagement.mapper;

import com.witchshop.sharedlib.dao.PipelineStepDefinition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PipelineStepDefinitionMapper {
    PipelineStepDefinition selectStepByPipelineIdAndStepNumber(@Param("pipelineId") Long pipelineId,
                                                               @Param("stepNumber") Integer stepNumber);
}
