package com.example.metering_simulator;

import com.example.metering_simulator.entity.Measurement;
import com.example.metering_simulator.rabbitmq.SenderService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
@Component
public class Simulator {

    private UUID deviceId;
    private static final String COMMA_DELIMITER = ",";
    private List<Measurement> measurementList;
    private SenderService senderService;

    @Autowired
    public Simulator(SenderService senderService, @Value("${device.id}") UUID deviceId) {
        this.senderService = senderService;
        this.deviceId = deviceId;
        this.measurementList = new ArrayList<>();
    }

    public void startSimulation() {
        readingCSV();
        for (Measurement measurement : measurementList) {
            senderService.send(measurement);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void readingCSV() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        try (Scanner scanner = new Scanner(new File("sensor.csv"))) {
            while (scanner.hasNextLine()) {
                measurementList.add(
                        getRecordFromLine(scanner.nextLine(),
                                currentTimestamp.getTime()));
                currentTimestamp.setTime(currentTimestamp.getTime() + TimeUnit.MINUTES.toMillis(10));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Measurement getRecordFromLine(String line, Long timestamp) {
        Measurement measurement = null;
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            if (rowScanner.hasNext()) {
                measurement = Measurement
                        .builder()
                        .timestamp(timestamp)
                        .deviceId(this.deviceId)
                        .measurementValue(Float.parseFloat(rowScanner.next()))
                        .build();
            }
        }
//        System.out.println(measurement);
        return measurement;
    }
}