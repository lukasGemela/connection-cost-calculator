package com.flixbus.costcalculator.service;

import com.flixbus.costcalculator.adapter.connections.ConnectionAdapter;
import com.flixbus.costcalculator.model.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.flixbus.costcalculator.fixtures.ConnectionFragmentFixtures.pilsenToNurembergConnectionFragmentsFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConnectionServiceImplTest {

    ConnectionService connectionService;

    @Mock
    ConnectionAdapter connectionAdapter;

    @BeforeEach
    void setUp() {
        this.connectionService = new ConnectionServiceImpl(connectionAdapter);
    }

    @Test
    void getConnection_shouldFetchConnectionFromAdapter() {

        final var connection = Connection.createConnection(pilsenToNurembergConnectionFragmentsFixture());

        when(connectionAdapter.getConnection("cityA", "cityB"))
                .thenReturn(connection);

        assertThat(connectionService.getConnection("cityA", "cityB"))
                .isEqualTo(connection);
    }
}