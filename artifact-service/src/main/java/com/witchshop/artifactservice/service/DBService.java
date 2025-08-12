package com.witchshop.artifactservice.service;

import com.witchshop.artifactservice.mapper.TaskExecutionMapper;
import com.witchshop.artifactservice.mapper.WorkerMapper;
import com.witchshop.sharedlib.dao.Workers;
import com.witchshop.sharedlib.enums.TaskStatuses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DBService {
    private final WorkerMapper workerMapper;
    private final TaskExecutionMapper taskExecutionMapper;

    @Transactional
    public Workers selectWorker() {
        return workerMapper.findAvailableWorker();
    }

    @Transactional
    public void updateWorkerInTask(UUID workerId, UUID taskId){
        taskExecutionMapper.updateWorkerInTask(workerId, taskId);
    }

    @Transactional
    public void updateTaskStatus(UUID id, TaskStatuses status) {
        taskExecutionMapper.updateTaskStatus(id, status);
    }

    @Transactional
    public void updateTaskAfterEnd(UUID id, TaskStatuses status, String result){
        taskExecutionMapper.updateTaskAfterEnd(id, status, result);
    }
}
