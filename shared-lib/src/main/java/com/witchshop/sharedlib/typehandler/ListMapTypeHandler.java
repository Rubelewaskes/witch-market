package com.witchshop.sharedlib.typehandler;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ListMapTypeHandler extends JsonTypeHandler<List<Map<String, Object>>> {
    public ListMapTypeHandler() {
        super(new TypeReference<List<Map<String, Object>>>() {});
    }

    @Override
    protected List<Map<String, Object>> getEmptyValue() {
        return Collections.emptyList();
    }
}
