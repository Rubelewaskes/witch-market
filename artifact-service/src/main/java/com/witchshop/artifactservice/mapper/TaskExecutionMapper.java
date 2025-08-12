package com.witchshop.artifactservice.mapper;

import com.witchshop.sharedlib.enums.TaskStatuses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface TaskExecutionMapper {
    void updateTaskStatus(@Param("id")UUID id, @Param("status")TaskStatuses status);
    void updateWorkerInTask(@Param("workerId")UUID workerId, @Param("taskId")UUID taskId);
    void updateTaskAfterEnd(@Param("id")UUID id, @Param("status")TaskStatuses status, @Param("result")String result);
}
