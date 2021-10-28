package com.flixbus.costcalculator.adapter.connections;

import com.flixbus.costcalculator.adapter.connections.api.CityEntity;
import com.flixbus.costcalculator.adapter.connections.api.ConnectionEntity;
import com.flixbus.costcalculator.model.Connection;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConnectionMapper {

    static List<Connection> toConnections(List<CityEntity> cityConnectionPath) {
        return cityConnectionPath
                .stream()
                .map(ConnectionMapper::toConnection)
                .flatMap(Optional::stream)
                .collect(Collectors.toUnmodifiableList());
    }

    private static Optional<Connection> toConnection(CityEntity cityEntity) {
        return getConnectionToNextCity(cityEntity)
                .map(connectionEntity -> new Connection(
                        cityEntity.getName(),
                        connectionEntity.getCity().getName(),
                        connectionEntity.getBusCostPerKm(),
                        connectionEntity.getDistance(),
                        connectionEntity.getDriverCostPerHr(),
                        connectionEntity.getDuration(),
                        connectionEntity.getLineId()
                        ));
    }

    private static Optional<ConnectionEntity> getConnectionToNextCity(CityEntity cityEntity) {
        return cityEntity.getConnections().stream().findFirst();
    }




}
