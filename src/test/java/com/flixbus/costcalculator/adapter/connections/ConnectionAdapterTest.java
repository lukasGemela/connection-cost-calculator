package com.flixbus.costcalculator.adapter.connections;

import com.flixbus.costcalculator.model.Connection;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.flixbus.costcalculator.fixtures.CityEntityFixtures.pilsenToNurembergCityEntityFixture;
import static com.flixbus.costcalculator.fixtures.ConnectionFragmentFixtures.pilsenToNurembergConnectionFragmentsFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConnectionAdapterTest {

    @Mock
    CityRepository cityRepository;

    private ConnectionAdapterImpl connectionAdapter;

    @BeforeEach
    void setUp() {
        this.connectionAdapter = new ConnectionAdapterImpl(cityRepository);
    }

    @Test
    void getConnection_shouldReturnConnection() {
        when(cityRepository.findAllOnShortestPathBetween("Pilsen", "Nürnberg"))
                .thenReturn(pilsenToNurembergCityEntityFixture());

        assertThat(connectionAdapter.getConnection("Pilsen", "Nürnberg"))
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder().withIgnoreCollectionOrder(true).build())
                .isEqualTo(Connection.createConnection(pilsenToNurembergConnectionFragmentsFixture()));
    }

    @Test
    void getConnection_pathIsEmpty_shouldReturnEmptyList() {
        when(cityRepository.findAllOnShortestPathBetween("Pilsen", "Nürnberg"))
                .thenReturn(List.of());
        assertThat(connectionAdapter.getConnection("Pilsen", "Nürnberg")).isEmpty();
    }
}
