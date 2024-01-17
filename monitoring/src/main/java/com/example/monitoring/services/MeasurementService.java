package com.example.monitoring.services;

import com.example.monitoring.entities.Measurement;
import com.example.monitoring.entities.UserDevice;
import com.example.monitoring.entities.dtos.MeasurementRequest;
import com.example.monitoring.repositories.MeasurementRepository;
import com.example.monitoring.repositories.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final ArrayList<Double> measurementValues = new ArrayList<>(); // List to store 10 minute sum


    @RabbitListener(queues = "messages_queue")
    public void saveMeasurement(MeasurementRequest measurementRequest) {

        System.out.println("Received measurement: { "
                + measurementRequest.getTimestamp()
                + ", " + measurementRequest.getDeviceId()
                + ", " + measurementRequest.getMeasurementValue() + " }");
        calculateHourlyConsumption(measurementRequest);
    }

    public void calculateHourlyConsumption(MeasurementRequest measurementRequest) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(measurementRequest.getTimestamp()), ZoneOffset.UTC);
        int hour = dateTime.getHour();
        int tenMinuteSlot = dateTime.getMinute() / 10;
        System.out.println("Hour: " + hour + ", tenMinuteSlot: " + tenMinuteSlot);
        if (tenMinuteSlot == 5) {
            System.out.println(measurementValues);
            Optional<Double> max = measurementValues.stream().max(Double::compare);
            Optional<Double> min = measurementValues.stream().min(Double::compare);

            if (max.isPresent()) {
                double maxValue = max.get();
                double minValue = min.get();
                double hourlyConsumptionValue = maxValue - minValue;
                UserDevice userDevice = userDeviceRepository.findById(measurementRequest.getDeviceId())
                        .orElse(null);
                if(userDevice == null) {
                    System.out.println("Device not found");
                    return;
                }
                Measurement measurement = Measurement.builder()
                        .timestamp(measurementRequest.getTimestamp())
                        .device(userDevice)
                        .measurementValue(hourlyConsumptionValue)
                        .build();
                try{
                    measurementRepository.save(measurement);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Measurement already exists. Service- saveMeasurement");
                }
            }
            measurementValues.clear();
        } else {
            measurementValues.add(measurementRequest.getMeasurementValue());
        }
    }
}
