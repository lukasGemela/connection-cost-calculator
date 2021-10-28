package com.flixbus.costcalculator.service;

import com.flixbus.costcalculator.model.Connection;

import java.util.List;

public interface ConnectionService {

    List<Connection> getConnection(String cityA, String cityB);
}
