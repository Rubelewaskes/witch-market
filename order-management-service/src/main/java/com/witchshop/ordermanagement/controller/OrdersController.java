package com.witchshop.ordermanagement.controller;

import com.witchshop.ordermanagement.entity.NewOrder;
import com.witchshop.ordermanagement.service.CoordinatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final CoordinatorService coordinatorService;

    @PostMapping("/")
    public void newOrder(@RequestBody NewOrder newOrder) {
        coordinatorService.createNewOrder(newOrder);
    }
}