package com.flixbus.costcalculator.controller.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GcpBucketChangeRequest {
    @JsonProperty(value = "name", required = true)
    private final String name;
    @JsonProperty(value = "bucket", required = true)
    private final String bucket;
    @JsonProperty(value = "contentType", required = true)
    private final String contentType;
    @JsonProperty(value = "generation", required = true)
    private final String generation;
    @JsonProperty(value = "size", required = true)
    private final String size;
}
