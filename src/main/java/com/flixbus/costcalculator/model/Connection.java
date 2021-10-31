package com.flixbus.costcalculator.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor(access=AccessLevel.PRIVATE)
public class Connection {

    private final List<ConnectionFragment> connectionFragments;

    public static Optional<Connection> createConnection(List<ConnectionFragment> connectionFragments) {
        if (connectionFragments.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(new Connection(connectionFragments));
        }
    }

    public int getNumberOfLines() {
        return connectionFragments
                .stream()
                .map(ConnectionFragment::getLineId)
                .collect(Collectors.toUnmodifiableSet())
                .size();
    }

    public double getTotalBusCost() {
        return connectionFragments
                .stream()
                .map(Connection::calculateBusCost)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public double getTotalDriverCost() {
        return connectionFragments
                .stream()
                .map(Connection::calculateDriverCost)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public double getTotalCost() {
        return getTotalBusCost() + getTotalDriverCost();
    }

    private static double calculateDriverCost(ConnectionFragment connectionFragment) {
        return connectionFragment.getDriverCostPerHr() * connectionFragment.getDuration();
    }

    private static double calculateBusCost(ConnectionFragment connectionFragment) {
        return connectionFragment.getBusCostPerKm() * connectionFragment.getDistance();
    }
}
