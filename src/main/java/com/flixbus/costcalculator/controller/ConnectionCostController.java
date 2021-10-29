package com.flixbus.costcalculator.controller;

import com.flixbus.costcalculator.controller.api.ConnectionCostResponse;
import com.flixbus.costcalculator.model.Connection;
import com.flixbus.costcalculator.model.ConnectionCost;
import com.flixbus.costcalculator.service.ConnectionService;
import com.flixbus.costcalculator.service.CostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConnectionCostController {

    private final ConnectionService connectionService;
    private final CostService costService;

    public ConnectionCostController(ConnectionService pathCostService, CostService costService) {
        this.connectionService = pathCostService;
        this.costService = costService;
    }

    @GetMapping(path="/connection/cost")
    @ApiOperation(value = "Get cost of bus connection between two cities.")
    public ConnectionCostResponse getConnectionCost(
                                 @RequestParam("originCity") String originCity,
                                  @RequestParam("destinationCity") String destinationCity) {


        final var connection = connectionService.getConnection(originCity, destinationCity);

        if (connection.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Connection between %s and %s could not be found", originCity, destinationCity));
        } else {
            return toConnectionCostResponse(costService.calculateCost(connection), connection);
        }
    }

    private static ConnectionCostResponse toConnectionCostResponse(ConnectionCost connectionCost, List<Connection> connection) {
        return new ConnectionCostResponse(
            getNumberOfLines(connection),
                connectionCost.getTotalBusCost(),
                connectionCost.getTotalDriverCost(),
                connectionCost.getTotalCost()
        );
    }

    private static int getNumberOfLines(List<Connection> connection) {
        return connection
                .stream()
                .map(Connection::getLineId)
                .collect(Collectors.toUnmodifiableSet())
                .size();
    }
}
