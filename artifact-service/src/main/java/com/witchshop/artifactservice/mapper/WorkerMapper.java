package com.witchshop.artifactservice.mapper;

import com.witchshop.sharedlib.dao.Workers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface WorkerMapper {
    Workers findAvailableWorker();
    void insertWorker(@Param("worker")Workers worker);
    void updateActivity(@Param("id")UUID id);
    //TODO Delete метод
}
