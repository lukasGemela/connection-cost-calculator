package com.flixbus.costcalculator.controller;

import com.flixbus.costcalculator.controller.api.ConnectionCostResponse;
import com.flixbus.costcalculator.model.Connection;
import com.flixbus.costcalculator.service.ConnectionService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConnectionCostController {

    private final ConnectionService connectionService;

    @GetMapping(path="/connection/cost")
    @ApiOperation(value = "Get cost of bus connection between two cities.")
    public ConnectionCostResponse getConnectionCost(
                                 @RequestParam("originCity") String originCity,
                                  @RequestParam("destinationCity") String destinationCity) {
        final var originCityTrimmed = originCity.trim();
        final var destinationCityTrimmed = destinationCity.trim();

        validateInputs(originCityTrimmed, destinationCityTrimmed);

        return connectionService.getConnection(originCityTrimmed, destinationCityTrimmed)
                .map(ConnectionCostController::toConnectionCostResponse)
                .orElseThrow(connectionDoesNotExistException(originCity, destinationCity));
    }

    private static void validateInputs(String originCityTrimmed, String destinationCityTrimmed) {
        if (!StringUtils.hasText(originCityTrimmed) || !StringUtils.hasText(destinationCityTrimmed)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City name cannot be empty");
        }

        if (originCityTrimmed.equals(destinationCityTrimmed)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City names must differ");
        }
    }

    private static Supplier<RuntimeException> connectionDoesNotExistException(String originCity, String destinationCity) {
        return () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Connection between %s and %s could not be found", originCity, destinationCity));
    }

    private static ConnectionCostResponse toConnectionCostResponse(Connection connection) {
        return new ConnectionCostResponse(
                connection.getNumberOfLines(),
                connection.getTotalBusCost(),
                connection.getTotalDriverCost(),
                connection.getTotalCost()
        );
    }
}
