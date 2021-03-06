package com.flixbus.costcalculator.adapter.connections;

import com.flixbus.costcalculator.adapter.connections.api.CityEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface CityRepository extends Neo4jRepository<CityEntity, Long> {
    @Query(""
            + "MATCH p = shortestPath((c1:City {name: $originCityName})-[:IS_CONNECTED*..15]-(c2:City {name: $destinationCityName}))\n"
            + "RETURN p"
    )
    List<CityEntity> findAllOnShortestPathBetween(@Param("originCityName") String originCityName, @Param("destinationCityName") String destinationCityName);

}
