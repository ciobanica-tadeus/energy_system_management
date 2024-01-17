package ro.tuc.ds2020.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.repository.DeviceRepository;

@RequiredArgsConstructor
@Component
public class OnStartUpConfig {
    private final DeviceRepository deviceRepository;

    //    @PostConstruct
    public void atStart() {

    }
}


