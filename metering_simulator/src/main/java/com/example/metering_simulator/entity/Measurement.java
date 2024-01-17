package com.example.metering_simulator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Measurement {
    private Long timestamp;
    private UUID deviceId;
    private Float measurementValue;
}
