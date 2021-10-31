package com.flixbus.costcalculator.service;

import com.flixbus.costcalculator.model.ConnectionCost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.flixbus.costcalculator.fixtures.ConnectionFixtures.pilsenToNurembergConnectionFixture;
import static com.flixbus.costcalculator.fixtures.ConnectionFixtures.singleLineBetweenMultipleCitiesFixture;
import static org.assertj.core.api.Assertions.assertThat;

class CostServiceImplTest {

    private CostService costService;

    @BeforeEach
    void setUp() {
        this.costService = new CostServiceImpl();
    }

    @Test
    void calculateCost_shouldCalculateTotalCost() {
        assertThat(costService.calculateCost(pilsenToNurembergConnectionFixture()))
                .usingRecursiveComparison()
                .isEqualTo(new ConnectionCost(250.0, 250.0, 500.0, 2));
    }

    @Test
    void calculateCost_oneLineBetweenMultipleCities_shouldCountOnlyOnce() {
        assertThat(costService.calculateCost(singleLineBetweenMultipleCitiesFixture()))
                .usingRecursiveComparison()
                .isEqualTo(new ConnectionCost(410.0, 410.0, 820.0, 2));
    }


    @Test
    void calculateCost_connectionEmpty_shouldReturnZeroCost() {
        assertThat(costService.calculateCost(List.of()))
                .usingRecursiveComparison()
                .isEqualTo(new ConnectionCost(0.0, 0.0, 0.0, 0));
    }
}