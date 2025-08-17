package com.witchshop.ordermanagement.controller;

import com.witchshop.ordermanagement.entity.TaskStatus;
import com.witchshop.ordermanagement.service.CoordinatorService;
import com.witchshop.sharedlib.dao.Workers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TasksController {
    private final CoordinatorService coordinatorService;

    @GetMapping
    //Вывод воркеров по типу
    public ResponseEntity<List<Workers>> getTasksByWorkerType(@RequestParam("worker_type") String workerType) {
        List<Workers> worker = coordinatorService.getWorkersByType(workerType);
        return ResponseEntity.ok(worker);
    }
    
    @GetMapping("/{id}")
    //Вывод информации о задаче
    public ResponseEntity<TaskStatus> getTaskById(@PathVariable String id) {
        TaskStatus taskStatus = coordinatorService.getTaskStatus(id);
        return ResponseEntity.ok(taskStatus);
    }
}