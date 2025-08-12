package com.witchshop.sharedlib.dao;

import com.witchshop.sharedlib.enums.OrderStatuses;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class Order {
    private UUID id;
    private Long pipelineId;
    private OrderStatuses status;
    private UUID clientId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp completedAt;
}

