package ro.tuc.ds2020.services;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.entities.dtos.DeviceRequest;
import ro.tuc.ds2020.entities.dtos.DeviceResponse;
import ro.tuc.ds2020.entities.dtos.ListDeviceResponse;
import ro.tuc.ds2020.entities.dtos.UserDeviceDTO;
import ro.tuc.ds2020.repository.DeviceRepository;
import ro.tuc.ds2020.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final SenderService senderService;
    private final UserRepository userRepository;

    public DeviceService(DeviceRepository deviceRepository, SenderService senderService,
                         UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.senderService = senderService;
        this.userRepository = userRepository;
    }

    public boolean saveDevice(DeviceRequest deviceRequest) {
        Optional<User> userResponse = userRepository.findById(deviceRequest.getUserId());
        if (userResponse.isEmpty()) {
            System.out.println("The user doesn't exist!");
            return false;
        }
        User user = userResponse.get();
        user.setId(deviceRequest.getUserId());
        System.out.println("#### User get with id : " + user.getId());
        Device device = Device.builder()
                .name(deviceRequest.getName())
                .description(deviceRequest.getDescription())
                .address(deviceRequest.getAddress())
                .maxHourlyEnergyConsumption(deviceRequest.getMaxHourlyEnergyConsumption())
                .user(user)
                .build();
        try {
            System.out.println("#### Saving device..." + device.toString());
            Device storedDevice = deviceRepository.save(device);
            System.out.println("#### Device saved successfully! - " + storedDevice.getId());
            UserDeviceDTO userDeviceDTO = UserDeviceDTO.builder()
                    .userId(deviceRequest.getUserId())
                    .deviceId(storedDevice.getId())
                    .maxHourlyEnergyConsumption(storedDevice.getMaxHourlyEnergyConsumption().doubleValue())
                    .operation("save")
                    .build();
            senderService.send(userDeviceDTO);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Device already exists. Service- saveDevice");
            return false;
        }
    }

    public ListDeviceResponse getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        ArrayList<DeviceResponse> listDevices = new ArrayList<>();
        for (Device device : devices) {
            DeviceResponse userResponse = DeviceResponse
                    .builder()
                    .id(device.getId())
                    .userId(device.getUser().getId())
                    .name(device.getName())
                    .description(device.getDescription())
                    .address(device.getAddress())
                    .maxHourlyEnergyConsumption(device.getMaxHourlyEnergyConsumption())
                    .build();
            listDevices.add(userResponse);
        }
        return new ListDeviceResponse(listDevices);

    }

    public boolean deleteDevice(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isEmpty()) {
            return false;
        }
        try {
            deviceRepository.deleteById(id);
            UserDeviceDTO userDeviceDTO = UserDeviceDTO.builder()
                    .deviceId(id)
                    .operation("delete")
                    .build();
            senderService.send(userDeviceDTO);
            return true;
        } catch (Exception e) {
            System.out.println("An error occurs on device deletion- DeviceUser - deleteDevice");
            return false;
        }
    }

    public DeviceResponse updateDevice(DeviceRequest deviceRequest) {
        Optional<Device> device = deviceRepository.findById(deviceRequest.getId());
        if (device.isEmpty()) {
            System.out.println("The device doesn't exist!");
            return null;
        }
        Device newDevice = device.get();
        newDevice.setUser(deviceRequest.getUserId() != null ?
                new User(deviceRequest.getUserId()) : newDevice.getUser());
        newDevice.setName(deviceRequest.getName() != null ?
                deviceRequest.getName() : newDevice.getName());
        newDevice.setDescription(deviceRequest.getDescription() != null ?
                deviceRequest.getDescription() : newDevice.getDescription());
        newDevice.setAddress(deviceRequest.getAddress() != null ?
                deviceRequest.getAddress() : newDevice.getAddress());
        newDevice.setMaxHourlyEnergyConsumption(deviceRequest.getMaxHourlyEnergyConsumption() != null ?
                deviceRequest.getMaxHourlyEnergyConsumption() : newDevice.getMaxHourlyEnergyConsumption());
        try {
            deviceRepository.save(newDevice);
            return DeviceResponse.builder()
                    .userId(newDevice.getUser().getId())
                    .name(newDevice.getName())
                    .description(newDevice.getDescription())
                    .address(newDevice.getAddress())
                    .maxHourlyEnergyConsumption(newDevice.getMaxHourlyEnergyConsumption())
                    .build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Device can't be updated . Service- updateDevice");
            return null;
        }
    }

    public ListDeviceResponse getDevicesOfUser(UUID id) {
        List<Device> devices = deviceRepository.getDevicesByUser_Id(id);
        ArrayList<DeviceResponse> listDevices = new ArrayList<>();
        for (Device device : devices) {
            DeviceResponse userResponse = DeviceResponse
                    .builder()
                    .id(device.getId())
                    .name(device.getName())
                    .description(device.getDescription())
                    .address(device.getAddress())
                    .maxHourlyEnergyConsumption(device.getMaxHourlyEnergyConsumption())
                    .build();
            listDevices.add(userResponse);
        }
        return new ListDeviceResponse(listDevices);
    }
}
