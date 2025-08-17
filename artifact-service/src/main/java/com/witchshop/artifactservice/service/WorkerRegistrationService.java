package com.witchshop.artifactservice.service;

import com.witchshop.artifactservice.mapper.WorkerMapper;
import com.witchshop.sharedlib.dao.Workers;
import com.witchshop.sharedlib.enums.Specialization;
import com.witchshop.sharedlib.enums.WorkerStatuses;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//TODO создание воркеров по потокам, удаление воркеров при отключении

@Service
@RequiredArgsConstructor
public class WorkerRegistrationService {
    private final Workers worker = new Workers();
    private final WorkerMapper workerMapper;

    @PostConstruct
    public void registerWorker() {
        this.worker.setStatus(WorkerStatuses.ACTIVE);
        this.worker.setSpecialization(Specialization.ARTIFACT);
        this.worker.setThreadPoolSize(1);

        workerMapper.insertWorker(this.worker);
    }
    // TODO не работает шедулер
    @Scheduled(fixedRate = 15*1000)
    public void updateLastActivity() {
        workerMapper.updateActivity(worker.getId());
    }
}
