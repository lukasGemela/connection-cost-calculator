package com.flixbus.costcalculator.fixtures;

import com.flixbus.costcalculator.model.Connection;

import java.util.List;

public class ConnectionFixtures {

    public static List<Connection> pilsenToNurembergConnectionFixture() {

        return List.of(new Connection(
                        "Pilsen",
                        "Rome",
                        3.0,
                        30,
                        30.0,
                        3,
                        4
                ),
                new Connection(
                        "Rome",
                        "Nürnberg",
                        4.0,
                        40,
                        40.0,
                        4,
                        2
                ));
    }

    public static List<Connection> singleLineBetweenMultipleCitiesFixture() {

        return List.of(new Connection(
                        "Pilsen",
                        "Rome",
                        3.0,
                        30,
                        30.0,
                        3,
                        4
                ),
                new Connection(
                        "Rome",
                        "Nürnberg",
                        4.0,
                        40,
                        40.0,
                        4,
                        2
                ),
                new Connection(
                        "Nürnberg",
                        "Budapest",
                        4.0,
                        40,
                        40.0,
                        4,
                        2
                ));
    }
}
