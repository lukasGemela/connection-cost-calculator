package com.flixbus.costcalculator.adapter.connections;

import com.flixbus.costcalculator.model.Connection;

import java.util.Optional;

public interface ConnectionAdapter {
    Optional<Connection> getConnection(String originCity, String destinationCity);
}
