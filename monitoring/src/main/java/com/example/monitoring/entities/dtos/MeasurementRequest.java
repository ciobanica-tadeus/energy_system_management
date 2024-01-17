package com.example.monitoring.entities.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MeasurementRequest {
    private Long timestamp;
    private UUID deviceId;
    private Double measurementValue;
}
