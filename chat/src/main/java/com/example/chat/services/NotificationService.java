package com.example.chat.services;

import com.example.chat.services.dtos.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendPrivateMessages(UUID userId, String content){
        MessageDto messageDto = MessageDto.builder()
                .content(content)
                .build();
        messagingTemplate.convertAndSend("/topic/user/" + userId.toString() + "/private-messages", messageDto);
    }
}
