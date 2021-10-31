package com.flixbus.costcalculator.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.flixbus.costcalculator.fixtures.ConnectionFragmentFixtures.pilsenToNurembergConnectionFragmentsFixture;
import static com.flixbus.costcalculator.fixtures.ConnectionFragmentFixtures.singleLineBetweenMultipleCitiesFixture;
import static org.assertj.core.api.Assertions.assertThat;

class ConnectionTest {

    @Test
    void createConnection_noFragments_shouldReturnEmptyOptional() {
        assertThat(Connection.createConnection(List.of())).isEmpty();
    }

    @Test
    void createConnection_someFragments_shouldReturnConnection() {
        assertThat(Connection.createConnection(pilsenToNurembergConnectionFragmentsFixture())).isNotEmpty();
    }

    @Test
    void getNumberOfLines_shouldCalculateNumbeOfLines() {
        assertThat(Connection.createConnection(pilsenToNurembergConnectionFragmentsFixture()).map(Connection::getNumberOfLines)).contains(2);
    }

    @Test
    void getNumberOfLines_oneLineBetweenMultipleCities_shouldCountOnlyOnce() {
        assertThat(Connection.createConnection(singleLineBetweenMultipleCitiesFixture()).map(Connection::getNumberOfLines)).contains(2);
    }

    @Test
    void getTotalBusCost_shouldReturnTotalCost() {
        assertThat(Connection.createConnection(pilsenToNurembergConnectionFragmentsFixture()).map(Connection::getTotalBusCost)).contains(250.0);
    }

    @Test
    void getTotalDriverCost_shouldReturnTotalDriverCost() {
        assertThat(Connection.createConnection(pilsenToNurembergConnectionFragmentsFixture()).map(Connection::getTotalDriverCost)).contains(250.0);
    }

    @Test
    void getTotalCost_shouldReturnTotalCost() {
        assertThat(Connection.createConnection(pilsenToNurembergConnectionFragmentsFixture()).map(Connection::getTotalCost)).contains(500.0);
    }
}