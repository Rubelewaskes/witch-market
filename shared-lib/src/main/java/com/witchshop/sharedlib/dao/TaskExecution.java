package com.witchshop.sharedlib.dao;

import com.witchshop.sharedlib.enums.Specialization;
import com.witchshop.sharedlib.enums.TaskStatuses;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class TaskExecution {
    private UUID id;
    private UUID orderId;
    private Integer stepNumber;
    private Specialization specialization;
    private TaskStatuses status;
    private UUID workerId;
    private Timestamp startedAt;
    private Timestamp completedAt;
    private String resultData;
}
