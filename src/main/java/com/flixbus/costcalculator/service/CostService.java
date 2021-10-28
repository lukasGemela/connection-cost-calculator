package com.flixbus.costcalculator.service;

import com.flixbus.costcalculator.model.Connection;
import com.flixbus.costcalculator.model.ConnectionCost;

import java.util.List;

public interface CostService {
    ConnectionCost calculateCost(List<Connection> connections);
}
