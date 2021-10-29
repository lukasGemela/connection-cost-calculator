package com.flixbus.costcalculator.adapter.connections;

import com.flixbus.costcalculator.adapter.connections.api.CityEntity;
import com.flixbus.costcalculator.adapter.connections.api.ConnectionEntity;
import com.flixbus.costcalculator.model.Connection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ConnectionAdapterImpl implements ConnectionAdapter {

    private final CityRepository cityRepository;

    public List<Connection> getConnection(String originCity, String destinationCity) {

        final var citiesOnPath = cityRepository.findAllOnShortestPathBetween(originCity, destinationCity);
        return toConnections(citiesOnPath);
    }

    private static List<Connection> toConnections(List<CityEntity> cityConnectionPath) {
        return cityConnectionPath
                .stream()
                .map(ConnectionAdapterImpl::toConnection)
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
