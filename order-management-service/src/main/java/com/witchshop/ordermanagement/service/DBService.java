package com.witchshop.ordermanagement.service;

import com.witchshop.ordermanagement.entity.NewOrder;
import com.witchshop.ordermanagement.entity.OrderCreationResult;
import com.witchshop.ordermanagement.enums.Specialization;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DBService {
    public OrderCreationResult createNewOrder(NewOrder newOrder){
        //TODO Из БД всё забирать
        Map<String, Object> requirements = new LinkedHashMap<>();
        requirements.put("temperature", 1500);
        requirements.put("magicLevel", "high");
        return new OrderCreationResult(
                UUID.fromString("a1b2c3d4-1234-5678-9012-abcdef123456"),
                newOrder.getPipelineId(),
                "Non-Hand-off",
                Specialization.ARTIFACT,
                Arrays.asList("dragon_scale", "mithril_ingot"),
                requirements
        );
    }
    public Optional<Integer> getNextStep(Long pipelineId, Integer stepId){
        //TODO Сначала, конечно, идёт в БД, чтобы понять, есть ли следующий шаг и кидает Optional.of(value)
        return Optional.empty();
    }
}
