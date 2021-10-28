package com.flixbus.costcalculator.utils.logging;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class MdcContext {

    public static final String REQUEST_ID = "REQUEST_ID";

    public void setLoggingContext(String requestId) {
        MDC.put(REQUEST_ID, requestId);
    }

    public String getRequestId() {
        return getContextMap().get(REQUEST_ID);
    }

    public void clearContext() {
        MDC.clear();
    }

    public Map<String, String> getContextMap() {
        return Optional.ofNullable(MDC.getCopyOfContextMap()).orElseGet(Map::of);
    }

    public void setContextMap(Map<String, String> contextMap) {
        MDC.setContextMap(contextMap);
    }


}