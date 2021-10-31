package com.flixbus.costcalculator.service;

import com.flixbus.costcalculator.model.Connection;

import java.util.Optional;

public interface ConnectionService {

    Optional<Connection> getConnection(String cityA, String cityB);
}
