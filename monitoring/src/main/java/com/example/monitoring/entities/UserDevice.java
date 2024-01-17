package com.example.monitoring.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity(name = "measurements_devices")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDevice {
    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "maxHourlyEnergyConsumption")
    private Double maxHourlyEnergyConsumption;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    private List<Measurement> measurements;

    public UserDevice(UUID deviceId) {
        this.id = deviceId;
    }
}
