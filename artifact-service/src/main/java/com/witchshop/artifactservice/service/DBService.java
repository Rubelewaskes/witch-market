package com.witchshop.artifactservice.service;

import com.witchshop.artifactservice.mapper.TaskExecutionMapper;
import com.witchshop.artifactservice.mapper.WorkerMapper;
import com.witchshop.sharedlib.dao.PipelineStepDefinition;
import com.witchshop.sharedlib.dao.Workers;
import com.witchshop.sharedlib.enums.Specialization;
import com.witchshop.sharedlib.enums.TaskStatuses;
import com.witchshop.sharedlib.mapper.PipelineStepDefinitionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DBService {
    private final WorkerMapper workerMapper;
    private final TaskExecutionMapper taskExecutionMapper;
    private final PipelineStepDefinitionMapper pipelineStepDefinitionMapper;

    @Cacheable(value="workers")
    public Workers selectWorker() {
        return workerMapper.findAvailableWorker();
    }

    @CacheEvict(value = "workers", allEntries = true)
    public void insertWorker(Workers worker) {
        workerMapper.insertWorker(worker);
    }

    @CacheEvict(value = "workers", allEntries = true)
    public void updateWorkerActivity(UUID workerId) {
        workerMapper.updateActivity(workerId);
    }

    public void updateWorkerInTask(UUID workerId, UUID taskId) {
        taskExecutionMapper.updateWorkerInTask(workerId, taskId);
    }

    public void updateTaskStatus(UUID id, TaskStatuses status) {

        taskExecutionMapper.updateTaskStatus(id, status);
    }

    public void updateTaskAfterEnd(UUID id, TaskStatuses status, String result) {
        taskExecutionMapper.updateTaskAfterEnd(id, status, result);
    }

    @Cacheable(value="pipeline_step_definitions")
    public List<PipelineStepDefinition> selectBlueprints(){
        return pipelineStepDefinitionMapper.selectStepsBySpecialization(Specialization.ARTIFACT);
    }
}
