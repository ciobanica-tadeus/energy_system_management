package com.example.metering_simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class MeteringSimulatorApplication implements CommandLineRunner {
    @Autowired
    private final Simulator simulator = new Simulator();

    public static void main(String[] args) {
        SpringApplication.run(MeteringSimulatorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        simulator.startSimulation();
    }
}
