package com.witchshop.sharedlib.typehandler;

import com.witchshop.sharedlib.enums.TaskStatuses;

public class TaskStatusesTypeHandler extends EnumTypeHandler<TaskStatuses>{
    public TaskStatusesTypeHandler(){
        super(TaskStatuses.class);
    }
}
