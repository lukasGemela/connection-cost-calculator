package com.flixbus.costcalculator.service;

import com.flixbus.costcalculator.model.Path;
import com.flixbus.costcalculator.model.PathCost;

import java.util.List;
import java.util.Optional;

public interface PathService {

    Optional<List<Path>> getPath(String originCity, String destinationCity);
    PathCost calculatePathCost(List<Path> path);
}
