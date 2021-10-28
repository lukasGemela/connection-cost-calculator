package com.flixbus.costcalculator.controller;

import com.flixbus.costcalculator.service.ConnectionService;
import com.flixbus.costcalculator.service.CostService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public void getConnectionCost(
                                 @RequestParam("originCity") String originCity,
                                  @RequestParam("destinationCity") String destinationCity) {


        final var connection = connectionService.getConnection(originCity, destinationCity);

        if (connection.isEmpty()) {
            //throw Exception
        } else {
            final var connectionCost = costService.calculateCost(connection);

        }
    }
}
