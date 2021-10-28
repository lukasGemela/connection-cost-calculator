package com.flixbus.costcalculator.service;

import com.flixbus.costcalculator.model.Connection;
import com.flixbus.costcalculator.model.ConnectionCost;

import java.util.List;

public class CostServiceImpl implements CostService {
    @Override
    public ConnectionCost calculateCost(List<Connection> connections) {
        final double totalDriverCost = connections
                .stream()
                .map(CostServiceImpl::calculateDriverCost)
                .mapToDouble(Double::doubleValue)
                .sum();

        final double totalBusCost = connections
                .stream()
                .map(CostServiceImpl::calculateBusCost)
                .mapToDouble(Double::doubleValue)
                .sum();

        final double totalCost = totalBusCost + totalDriverCost;

        return new ConnectionCost(totalBusCost, totalDriverCost, totalCost);
    }

    private static double calculateDriverCost(Connection connection) {
        return connection.getDriverCostPerHr() * connection.getDuration();
    }

    private static double calculateBusCost(Connection connection) {
        return connection.getBusCostPerKm() * connection.getDistance();
    }
}
