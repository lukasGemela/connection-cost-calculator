package com.flixbus.costcalculator.controller;

import com.flixbus.costcalculator.service.ConnectionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConnectionCostController {

    private final ConnectionService pathCostService;

    public ConnectionCostController(ConnectionService pathCostService) {
        this.pathCostService = pathCostService;
    }

    @GetMapping(path="/connection/cost")
    public void getConnectionCost(
                                 @RequestParam("originCity") String originCity,
                                  @RequestParam("destinationCity") String destinationCity) {

        pathCostService.getPath(originCity, destinationCity);
    }
}
