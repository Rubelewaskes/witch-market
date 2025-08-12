package com.witchshop.sharedlib.dao;

import com.witchshop.sharedlib.enums.Specialization;
import com.witchshop.sharedlib.enums.WorkerStatuses;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class Workers {
    private UUID id;
    private Specialization specialization;
    private WorkerStatuses status;
    private Timestamp lastActivity;
    private Integer threadPoolSize;
}
