package com.flixbus.costcalculator.controller.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@JsonAutoDetect
public class ConnectionCostResponse {
    @ApiModelProperty(required = true)
    @JsonProperty(value = "num_lines")
    private final int numLines;
    @ApiModelProperty(required = true)
    @JsonProperty(value = "total_bus_cost")
    private final double totalBusCost;
    @ApiModelProperty(required = true)
    @JsonProperty(value = "total_driver_cost")
    private final double totalDriverCost;
    @ApiModelProperty(required = true)
    @JsonProperty(value = "total_cost")
    private final double totalCost;
}
