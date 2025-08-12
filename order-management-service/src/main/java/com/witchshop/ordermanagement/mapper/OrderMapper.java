package com.witchshop.ordermanagement.mapper;

import com.witchshop.sharedlib.dao.Order;
import com.witchshop.sharedlib.enums.OrderStatuses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface OrderMapper {
    void insertOrder(@Param("order") Order order);
    void updateOrderStatus(@Param("orderId") UUID orderId, @Param("orderStatus")OrderStatuses orderStatus);
}
