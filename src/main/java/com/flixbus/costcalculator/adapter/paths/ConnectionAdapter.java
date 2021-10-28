package com.flixbus.costcalculator.adapter.paths;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConnectionAdapter {

    private final CityRepository cityRepository;

    public void findShortestPath(String originCity, String destinationCity) {

        final var xxxxx = cityRepository.findAllOnShortestPathBetween(originCity, destinationCity);

        int i = 0;
    }
}
