package com.witchshop.ordermanagement.mapper;

import com.witchshop.ordermanagement.entity.TaskStatus;
import com.witchshop.sharedlib.dao.TaskExecution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface TaskExecutionMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertTask(@Param("task") TaskExecution task);
    TaskStatus getTaskStatusById(@Param("id")UUID id);
}
