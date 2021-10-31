package com.flixbus.costcalculator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.utility.MountableFile;

import java.net.URL;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CostCalculatorComponentTest {

    private static final URL TEST_DATA_FOLDER_URL = CostCalculatorComponentTest.class.getClassLoader().getResource("testdata/neo4j/csv");

    private static Neo4jContainer<?> NEO4J_CONTAINER;

    @Value("file:load_data.cypher")
    private Resource loadDataScript;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Neo4jClient neo4jClient;

    @BeforeAll
    static void initializeNeo4j() {
        NEO4J_CONTAINER = new Neo4jContainer<>("neo4j")
                .withAdminPassword("psw")
                .withFileSystemBind(Objects.requireNonNull(TEST_DATA_FOLDER_URL).getPath(), "/var/lib/neo4j/import/data");
        NEO4J_CONTAINER.start();
    }

    @AfterAll
    static void stopNeo4j() {
        NEO4J_CONTAINER.close();
    }

    @AfterEach
    void tearDown() {
        neo4jClient.query("MATCH (n)\nDETACH DELETE n;").run();
    }

    @Test
    void happyPath_returnsCostForExistingConnection() throws Exception {

        injectTestData(loadDataScript);

        mockMvc.perform(
                        get("/api/v1/connection/cost")
                                .param("originCity", "Prague")
                                .param("destinationCity", "Rome")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.num_lines").value(4))
                .andExpect(jsonPath("$.total_bus_cost").value(26114.7731782))
                .andExpect(jsonPath("$.total_driver_cost").value(322.368721924))
                .andExpect(jsonPath("$.total_cost").value(26437.141900124));
    }

    @Test
    void happyPath_directionReversed_returnsCostForExistingConnection() throws Exception {

        injectTestData(loadDataScript);

        mockMvc.perform(
                        get("/api/v1/connection/cost")
                                .param("originCity", "Rome")
                                .param("destinationCity", "Prague")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.num_lines").value(4))
                .andExpect(jsonPath("$.total_bus_cost").value(26114.7731782))
                .andExpect(jsonPath("$.total_driver_cost").value(322.368721924))
                .andExpect(jsonPath("$.total_cost").value(26437.141900124));
    }

    @Test
    void unhappyPath_nonExistingConnection_shouldReturn404() throws Exception {

        mockMvc.perform(
                        get("/api/v1/connection/cost")
                                .param("originCity", "Prague")
                                .param("destinationCity", "Rome"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason( "Connection between Prague and Rome could not be found"));
    }

    @Test
    void sadPath_originCityStringEmpty_shouldReturn400() throws Exception {

        mockMvc.perform(
                        get("/api/v1/connection/cost")
                                .param("originCity", "    ")
                                .param("destinationCity", "Rome"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("City name cannot be empty"));
    }

    @Test
    void sadPath_destinationCityStringEmpty_shouldReturn400() throws Exception {

        mockMvc.perform(
                        get("/api/v1/connection/cost")
                                .param("originCity", "Prague")
                                .param("destinationCity", "    "))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("City name cannot be empty"));
    }

    private static void injectTestData(Resource scriptFile) throws Exception {
        final var targetFileNamePath = "/var/lib/neo4j/import/" + UUID.randomUUID();
        NEO4J_CONTAINER.copyFileToContainer(MountableFile.forHostPath(scriptFile.getFile().getPath()), targetFileNamePath);
        final var result = NEO4J_CONTAINER.execInContainer("cypher-shell", "-f", targetFileNamePath, "--username", "neo4j", "--password", NEO4J_CONTAINER.getAdminPassword(), "--debug");
        if (result.getExitCode() != 0) {
            throw new RuntimeException(String.format("injecting test data failed, stdout: %s, stderr: %s", result.getStdout(), result.getStderr()));
        }
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.neo4j.uri", NEO4J_CONTAINER::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", NEO4J_CONTAINER::getAdminPassword);
    }

}
