package com.flixbus.costcalculator.service;

import com.flixbus.costcalculator.adapter.paths.ConnectionAdapter;
import com.flixbus.costcalculator.model.Path;
import com.flixbus.costcalculator.model.PathCost;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CostServiceImpl implements PathService {

    private final ConnectionAdapter connectionAdapter;


    @Override
    public Optional<List<Path>> getPath(String originCity, String destinationCity) {
        connectionAdapter.findShortestPath(originCity, destinationCity);
        return Optional.empty();
    }

    @Override
    public PathCost calculatePathCost(List<Path> path) {
        return null;
    }

}
