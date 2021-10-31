package com.flixbus.costcalculator.adapter.connections.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("City")
@AllArgsConstructor
@Getter
public class CityEntity {

    @Id
    @GeneratedValue
    private final Long id;

    @Property("name")
    private final String name;

    @Relationship(type = "IS_CONNECTED")
    private final List<ConnectionEntity> connections;
}
