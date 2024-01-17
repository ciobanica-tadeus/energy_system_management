package com.example.metering_simulator.rabbitmq;

import com.example.metering_simulator.entity.Measurement;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SenderService {

    private final RabbitTemplate rabbitTemplate;

    public void send(Measurement measurement) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.TOPIC_EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                measurement);

    }
}
