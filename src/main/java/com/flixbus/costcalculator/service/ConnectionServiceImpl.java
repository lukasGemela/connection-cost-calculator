package com.flixbus.costcalculator.service;

import com.flixbus.costcalculator.adapter.connections.ConnectionAdapter;
import com.flixbus.costcalculator.model.Connection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionAdapter connectionAdapter;

    @Override
    public Optional<Connection> getConnection(String cityA, String cityB) {
        return connectionAdapter.getConnection(cityA, cityB);
    }
}
