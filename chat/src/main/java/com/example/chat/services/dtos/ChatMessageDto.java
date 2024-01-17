package com.example.chat.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {
    private UUID senderId;
    private UUID receiverId;
    private LocalDateTime sentAt;
    private String content;
    private Boolean seen;
    private UUID messageExternalId;
}

