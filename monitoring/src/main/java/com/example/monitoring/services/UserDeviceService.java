package com.example.monitoring.services;

import com.example.monitoring.entities.UserDevice;
import com.example.monitoring.entities.dtos.UserDeviceRequest;
import com.example.monitoring.repositories.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeviceService {
    private final UserDeviceRepository userDeviceRepository;

    @RabbitListener(queues = "messages_sync_queue")
    public void saveDevice(UserDeviceRequest userDeviceRequest) {

        System.out.println("Received device: { "
                + userDeviceRequest.getOperation()
                + ", " + userDeviceRequest.getUserId()
                + ", " + userDeviceRequest.getDeviceId() + " }");

        if (userDeviceRequest.getOperation().equals("save")) {
            UserDevice userDevice = UserDevice.builder()
                    .userId(userDeviceRequest.getUserId())
                    .id(userDeviceRequest.getDeviceId())
                    .maxHourlyEnergyConsumption(userDeviceRequest.getMaxHourlyEnergyConsumption())
                    .build();
            userDeviceRepository.save(userDevice);
        } else if (userDeviceRequest.getOperation().equals("delete")) {
            userDeviceRepository.deleteById(userDeviceRequest.getDeviceId());
        }
    }
}
