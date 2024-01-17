package com.example.metering_simulator.rabbitmq;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RabbitMQController {

        private final SenderService senderService;


//        @PostMapping("/send")
//        public void send(@RequestBody Message message) {
//            senderService.send(message);
//        }
}
