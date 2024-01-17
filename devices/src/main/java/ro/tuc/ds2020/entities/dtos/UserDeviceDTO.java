package ro.tuc.ds2020.entities.dtos;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDeviceDTO {
    private String operation;
    private UUID userId;
    private UUID deviceId;
    private Double maxHourlyEnergyConsumption;
}
