package com.witchshop.ordermanagement.mapper;

import com.witchshop.sharedlib.dao.Workers;
import com.witchshop.sharedlib.enums.Specialization;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkerMapper {
    List<Workers> getWorkersBySpecialization(Specialization type);
}
