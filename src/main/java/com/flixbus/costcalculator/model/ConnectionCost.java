package com.flixbus.costcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ConnectionCost {
    private final double totalBusCost;
    private final double totalDriverCost;
    private final double totalCost;
}
