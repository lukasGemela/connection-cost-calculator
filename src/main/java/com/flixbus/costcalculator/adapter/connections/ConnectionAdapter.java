package com.flixbus.costcalculator.adapter.connections;

import com.flixbus.costcalculator.model.Connection;

import java.util.List;

public interface ConnectionAdapter {
    List<Connection> getConnection(String originCity, String destinationCity);
}
