package com.flixbus.costcalculator.utils.logging;

import java.util.UUID;

public interface RandomIdGenerator {

    default String generateRandomId() {
        return UUID.randomUUID().toString();
    }
}
