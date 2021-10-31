package com.flixbus.costcalculator.adapter.connections.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
@AllArgsConstructor
@Getter
public class ConnectionEntity {

    @Id
    @GeneratedValue
    private final Long id;
    @Property("bus_cost_per_km")
    private final double busCostPerKm;
    @Property("distance")
    private final int distance;
    @Property("driver_cost_per_hr")
    private final double driverCostPerHr;
    @Property("duration")
    private final double duration;
    @Property("line_id")
    private final int lineId;

    @TargetNode
    private final CityEntity city;
}
