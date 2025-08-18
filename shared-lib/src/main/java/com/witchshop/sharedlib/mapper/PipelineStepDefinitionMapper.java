package com.witchshop.sharedlib.mapper;

import com.witchshop.sharedlib.dao.PipelineStepDefinition;
import com.witchshop.sharedlib.enums.Specialization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PipelineStepDefinitionMapper {
    List<PipelineStepDefinition> selectStepsBySpecialization(@Param("specialization") Specialization specialization);
}
