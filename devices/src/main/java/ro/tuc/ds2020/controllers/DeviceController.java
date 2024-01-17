package ro.tuc.ds2020.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.entities.dtos.DeviceRequest;
import ro.tuc.ds2020.entities.dtos.DeviceResponse;
import ro.tuc.ds2020.entities.dtos.ListDeviceResponse;
import ro.tuc.ds2020.services.DeviceService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/devices")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping(value = "/save")
    public boolean saveDevice(@RequestBody DeviceRequest deviceRequest) {
        boolean response = deviceService.saveDevice(deviceRequest);
        if (response) {
            log.info("The device with the name {} was added with succes!", deviceRequest.getDescription());
            return true;
        } else {
            log.info("The device with the name {} already exists", deviceRequest.getDescription());
            return false;
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<ListDeviceResponse> getAllDevices() {
        ListDeviceResponse devicesList = deviceService.getAllDevices();
        if (devicesList.getDeviceResponseList().isEmpty()) {
            log.info("The devices table is empty! Try to add a device first!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(devicesList);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<DeviceResponse> updateUser(@RequestBody DeviceRequest deviceRequest) {
        DeviceResponse deviceResponse = deviceService.updateDevice(deviceRequest);
        if (deviceResponse != null) {
            log.info("Device with id {} was updated in db", deviceRequest.getId());
            return ResponseEntity.status(HttpStatus.OK).body(deviceResponse);
        } else {
            log.error("An error occurs on update a device");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") UUID id) {
        boolean deviceDeletion = deviceService.deleteDevice(id);
        if (deviceDeletion) {
            log.info("The device with id {} was deleted with succes!", id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } else {
            log.info("An error occurs with deletion of device with id {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @GetMapping(value = "/getDevices/{id}")
    public ResponseEntity<ListDeviceResponse> getDevicesOfUser(@PathVariable("id") UUID id) {
        ListDeviceResponse listDeviceResponse = deviceService.getDevicesOfUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(listDeviceResponse);
    }
}
