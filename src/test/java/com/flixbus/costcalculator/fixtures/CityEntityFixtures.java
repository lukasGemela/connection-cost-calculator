package com.flixbus.costcalculator.fixtures;

import com.flixbus.costcalculator.adapter.connections.api.CityEntity;
import com.flixbus.costcalculator.adapter.connections.api.ConnectionEntity;

import java.util.List;

public class CityEntityFixtures {

    public static List<CityEntity> pilsenToNurembergCityEntityFixture() {
        final var nuremberg = new CityEntity(0L, "Nürnberg", List.of());
        final var romeToNueConn = new ConnectionEntity(0L,4.0, 40, 40.0, 4, 2, nuremberg);
        final var rome = new CityEntity(0L, "Rome", List.of(romeToNueConn));
        final var pilsenToRomeConn = new ConnectionEntity(0L,3.0, 30, 30.0, 3, 4, rome);
        final var pilsen = new CityEntity(0L, "Pilsen", List.of(pilsenToRomeConn));

        return List.of(nuremberg, rome, pilsen);
    }
}
