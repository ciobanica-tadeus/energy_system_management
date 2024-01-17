package com.example.monitoring.entities.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter

public class UserDeviceRequest {
    private String operation;
    private UUID userId;
    private UUID deviceId;
    private Double maxHourlyEnergyConsumption;
}
