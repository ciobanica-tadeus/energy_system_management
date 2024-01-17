package ro.tuc.ds2020.entities.dtos;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceResponse {
    private UUID id;
    private UUID userId;
    private String name;
    private String description;
    private String address;
    private Long maxHourlyEnergyConsumption;
}
