package com.witchshop.artifactservice.service;

import com.witchshop.artifactservice.mapper.TaskExecutionMapper;
import com.witchshop.artifactservice.mapper.WorkerMapper;
import com.witchshop.sharedlib.dao.PipelineStepDefinition;
import com.witchshop.sharedlib.dao.Workers;
import com.witchshop.sharedlib.enums.Specialization;
import com.witchshop.sharedlib.enums.TaskStatuses;
import com.witchshop.sharedlib.mapper.PipelineStepDefinitionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DBService {
    private final WorkerMapper workerMapper;
    private final TaskExecutionMapper taskExecutionMapper;
    private final PipelineStepDefinitionMapper pipelineStepDefinitionMapper;

    public Workers selectWorker() {
        return workerMapper.findAvailableWorker();
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
    public List<PipelineStepDefinition> selectBlueprints(){
        return pipelineStepDefinitionMapper.selectStepsBySpecialization(Specialization.ARTIFACT);
    }
}
