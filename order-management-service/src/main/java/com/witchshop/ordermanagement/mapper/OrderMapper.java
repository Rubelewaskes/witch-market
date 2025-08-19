package com.witchshop.ordermanagement.mapper;

import com.witchshop.ordermanagement.entity.OrderStatus;
import com.witchshop.sharedlib.dao.Order;
import com.witchshop.sharedlib.enums.OrderStatuses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface OrderMapper {
    void insertOrder(@Param("order") Order order);
    void updateOrderStatus(@Param("orderId") UUID orderId, @Param("orderStatus")OrderStatuses orderStatus);
    void markOrderAsCompleted(@Param("id") UUID id);
    OrderStatus getOrderStatusById(@Param("id") UUID id);
}
