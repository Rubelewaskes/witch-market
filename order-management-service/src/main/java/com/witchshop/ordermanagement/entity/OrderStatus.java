package com.witchshop.ordermanagement.entity;

import com.witchshop.sharedlib.enums.OrderStatuses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatus {
    private UUID orderId;
    private OrderStatuses orderStatus;
    private List<TaskStatus> stepsStatuses;


}
