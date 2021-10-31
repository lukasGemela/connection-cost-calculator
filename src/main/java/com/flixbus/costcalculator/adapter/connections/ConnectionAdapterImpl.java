package com.flixbus.costcalculator.adapter.connections;

import com.flixbus.costcalculator.adapter.connections.api.CityEntity;
import com.flixbus.costcalculator.adapter.connections.api.ConnectionEntity;
import com.flixbus.costcalculator.model.Connection;
import com.flixbus.costcalculator.model.ConnectionFragment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ConnectionAdapterImpl implements ConnectionAdapter {

    private final CityRepository cityRepository;

    public Optional<Connection> getConnection(String originCity, String destinationCity) {

        final var citiesOnPath = cityRepository.findAllOnShortestPathBetween(originCity, destinationCity);
        return Connection.createConnection(toConnectionFragments(citiesOnPath));
    }

    private static List<ConnectionFragment> toConnectionFragments(List<CityEntity> cityConnectionPath) {
        return cityConnectionPath
                .stream()
                .map(ConnectionAdapterImpl::toConnectionFragment)
                .flatMap(Optional::stream)
                .collect(Collectors.toUnmodifiableList());
    }

    private static Optional<ConnectionFragment> toConnectionFragment(CityEntity cityEntity) {
        return getConnectionToNextCity(cityEntity)
                .map(connectionEntity -> new ConnectionFragment(
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
