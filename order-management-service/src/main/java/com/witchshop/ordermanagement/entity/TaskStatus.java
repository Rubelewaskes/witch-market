package com.witchshop.ordermanagement.entity;

import com.witchshop.sharedlib.enums.Specialization;
import com.witchshop.sharedlib.enums.TaskStatuses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatus {
    private UUID taskId;
    private Integer stepNumber;
    private Specialization specialization;
    private TaskStatuses taskStatus;
    private Timestamp startedAt;
    private Timestamp completedAt;
}
