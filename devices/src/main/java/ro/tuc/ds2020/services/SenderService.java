package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.config.RabbitMQConfig;
import ro.tuc.ds2020.entities.dtos.UserDeviceDTO;

@Service
@RequiredArgsConstructor
public class SenderService {

    private final RabbitTemplate rabbitTemplate;

    public void send(UserDeviceDTO userDeviceDTO) {
        System.out.println("Sending device: { "
                + userDeviceDTO.getUserId()
                + ", " + userDeviceDTO.getDeviceId() + " }");
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.SYNC_TOPIC_EXCHANGE_NAME,
                RabbitMQConfig.SYNC_ROUTING_KEY,
                userDeviceDTO);


    }
}
