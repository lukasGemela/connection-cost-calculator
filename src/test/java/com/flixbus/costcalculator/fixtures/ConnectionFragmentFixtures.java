package com.flixbus.costcalculator.fixtures;

import com.flixbus.costcalculator.model.ConnectionFragment;

import java.util.List;

public class ConnectionFragmentFixtures {

    public static List<ConnectionFragment> pilsenToNurembergConnectionFragmentsFixture() {

        return List.of(new ConnectionFragment(
                        "Pilsen",
                        "Rome",
                        3.0,
                        30,
                        30.0,
                        3,
                        4
                ),
                new ConnectionFragment(
                        "Rome",
                        "Nürnberg",
                        4.0,
                        40,
                        40.0,
                        4,
                        2
                ));
    }

    public static List<ConnectionFragment> singleLineBetweenMultipleCitiesFixture() {

        return List.of(new ConnectionFragment(
                        "Pilsen",
                        "Rome",
                        3.0,
                        30,
                        30.0,
                        3,
                        4
                ),
                new ConnectionFragment(
                        "Rome",
                        "Nürnberg",
                        4.0,
                        40,
                        40.0,
                        4,
                        2
                ),
                new ConnectionFragment(
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
