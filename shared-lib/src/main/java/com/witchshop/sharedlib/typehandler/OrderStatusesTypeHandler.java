package com.witchshop.sharedlib.typehandler;

import com.witchshop.sharedlib.enums.OrderStatuses;

public class OrderStatusesTypeHandler extends EnumTypeHandler<OrderStatuses> {
    public OrderStatusesTypeHandler() {
        super(OrderStatuses.class);
    }
}
