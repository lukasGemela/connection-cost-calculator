package com.flixbus.costcalculator.service;

import com.flixbus.costcalculator.model.Connection;
import com.flixbus.costcalculator.model.ConnectionCost;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
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

        return new ConnectionCost(totalBusCost, totalDriverCost, totalCost, getNumberOfLines(connections));
    }

    private static int getNumberOfLines(List<Connection> connection) {
        return connection
                .stream()
                .map(Connection::getLineId)
                .collect(Collectors.toUnmodifiableSet())
                .size();
    }

    private static double calculateDriverCost(Connection connection) {
        return connection.getDriverCostPerHr() * connection.getDuration();
    }

    private static double calculateBusCost(Connection connection) {
        return connection.getBusCostPerKm() * connection.getDistance();
    }
}
