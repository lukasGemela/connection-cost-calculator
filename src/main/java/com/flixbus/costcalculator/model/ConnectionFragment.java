package com.flixbus.costcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConnectionFragment {
    private final String cityA;
    private final String cityB;

    private final double busCostPerKm;
    private final int distance;
    private final double driverCostPerHr;
    private final double duration;
    private final int lineId;
}
