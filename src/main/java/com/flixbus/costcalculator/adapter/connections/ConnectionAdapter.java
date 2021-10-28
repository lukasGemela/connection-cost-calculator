package com.flixbus.costcalculator.adapter.connections;

import com.flixbus.costcalculator.model.Connection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ConnectionAdapter {

    private final CityRepository cityRepository;

    public List<Connection> findShortestPath(String originCity, String destinationCity) {

        final var citiesOnPath = cityRepository.findAllOnShortestPathBetween(originCity, destinationCity);
        return ConnectionMapper.toConnections(citiesOnPath);
    }
}
