package ro.tuc.ds2020.entities.dtos;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListDeviceResponse {
    private ArrayList<DeviceResponse> deviceResponseList;
}
