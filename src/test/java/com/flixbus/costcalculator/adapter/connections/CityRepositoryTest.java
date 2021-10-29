package com.flixbus.costcalculator.adapter.connections;

import com.flixbus.costcalculator.adapter.connections.api.CityEntity;
import com.flixbus.costcalculator.adapter.connections.api.ConnectionEntity;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.core.io.Resource;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.flixbus.costcalculator.utils.Utils.asString;
import static org.assertj.core.api.Assertions.assertThat;


@DataNeo4jTest
class CityRepositoryTest {

    private static Neo4j EMBEDDED_DATABASE_SERVER;

    @Value("classpath:testdata/neo4j/test_graph.cypher")
    private Resource testGraph;

    @Autowired
    private Neo4jClient client;

    @Autowired
    private CityRepository cityRepository;

    @BeforeAll
    static void initializeNeo4j() {

        EMBEDDED_DATABASE_SERVER = Neo4jBuilders.newInProcessBuilder()
                .withDisabledServer()
                .build();
    }


    @AfterAll
    static void stopNeo4j() {
        EMBEDDED_DATABASE_SERVER.close();
    }

    @AfterEach
    void tearDown() {
        client.query("MATCH (n)\nDETACH DELETE n").run();
    }

    @Test
    public void findAllOnShortestPathBetween_singlePathExist_shouldReturnShortestPath() {
        client.query(asString(testGraph)).run();

        final var result = cityRepository.findAllOnShortestPathBetween("Pilsen", "Nürnberg");

        assertThat(result)
                .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFieldsMatchingRegexes(".*id$")
                        .build())
                .hasSameElementsAs(pilsenToNurembergPath());
    }

    @Test
    public void findAllOnShortestPathBetween_directionReverted_shouldReturnShortestPath() {
        client.query(asString(testGraph)).run();

        final var result = cityRepository.findAllOnShortestPathBetween("Nürnberg", "Pilsen");

        assertThat(result)
                .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFieldsMatchingRegexes(".*id$")
                        .build())
                .hasSameElementsAs(pilsenToNurembergPath());
    }

    @Test
    public void findAllOnShortestPathBetween_pathDoesNotExist_shouldReturnEmpty() {
        client.query(asString(testGraph)).run();

        final var result = cityRepository.findAllOnShortestPathBetween("Prague", "Paris");

        assertThat(result).isEmpty();
    }

    @Test
    public void findAllOnShortestPathBetween_cityDoesNotExist_shouldReturnEmpty() {
        client.query(asString(testGraph)).run();

        final var result = cityRepository.findAllOnShortestPathBetween("xxxx", "yyyy");

        assertThat(result).isEmpty();
    }

    private static List<CityEntity> pilsenToNurembergPath() {
        final var nuremberg = new CityEntity(0L, "Nürnberg", List.of());
        final var romeToNueConn = new ConnectionEntity(0L,4.0, 40, 40.0, 4, 2, nuremberg);
        final var rome = new CityEntity(0L, "Rome", List.of(romeToNueConn));
        final var pilsenToRomeConn = new ConnectionEntity(0L,3.0, 30, 30.0, 3, 4, rome);
        final var pilsen = new CityEntity(0L, "Pilsen", List.of(pilsenToRomeConn));

        return List.of(nuremberg, rome, pilsen);
    }


    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.neo4j.uri", EMBEDDED_DATABASE_SERVER::boltURI);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> null);
    }



}