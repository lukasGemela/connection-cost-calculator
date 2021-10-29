package com.flixbus.costcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Connection {
    private final String cityA;
    private final String cityB;

    private final double busCostPerKm;
    private final int distance;
    private final double driverCostPerHr;
    private final int duration;
    private final int lineId;
}
