package com.flixbus.costcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ConnectionCost {
    private double totalBusCost;
    private double totalDriverCost;
    private double totalCost;
}
