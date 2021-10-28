package com.flixbus.costcalculator.controller;

import com.flixbus.costcalculator.service.PathService;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConnectionCostController {

    private final PathService pathCostService;

    public ConnectionCostController(PathService pathCostService) {
        this.pathCostService = pathCostService;
    }

    @GetMapping(path="/connection/cost")
    public void getConnectionCost(
                                 @RequestParam("originCity") String originCity,
                                  @RequestParam("destinationCity") String destinationCity) {

        pathCostService.getPath(originCity, destinationCity);
    }
}
