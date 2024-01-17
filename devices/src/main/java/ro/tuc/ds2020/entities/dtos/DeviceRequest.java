package ro.tuc.ds2020.entities.dtos;

import lombok.Getter;
import lombok.Setter;
import ro.tuc.ds2020.entities.User;

import java.util.UUID;

@Getter
@Setter
public class DeviceRequest {
    private UUID id;
    private UUID userId;
    private String name;
    private String description;
    private String address;
    private Long maxHourlyEnergyConsumption;
}
