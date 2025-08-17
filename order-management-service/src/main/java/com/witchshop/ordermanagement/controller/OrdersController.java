package com.witchshop.ordermanagement.controller;

import com.witchshop.ordermanagement.entity.NewOrder;
import com.witchshop.ordermanagement.entity.OrderStatus;
import com.witchshop.ordermanagement.service.CoordinatorService;
import com.witchshop.sharedlib.dao.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final CoordinatorService coordinatorService;

    @PostMapping
    //Создание заказа
    public void newOrder(@RequestBody NewOrder newOrder) {
        coordinatorService.createNewOrder(newOrder);
    }

    @GetMapping("/{id}")
    //Просмотр статуса заказа (возвращает состояние заказа и каждого из этапов)
    public ResponseEntity<OrderStatus> GetOrderInfo(@PathVariable String id){
        try {
            OrderStatus order = coordinatorService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID");
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException e) {
            log.error("Order not found");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Internal Server Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    //TODO просомтр заказов с фильтрацей
    public ResponseEntity<List<Order>> getOrders(@RequestParam(required = false) Map<String, String> filters){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancel")
    //TODO отмена заказа
    public ResponseEntity<Void> cancelOrder(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }
}