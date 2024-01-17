package com.example.chat.services.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendMessageRequest {
    @NotNull
    private UUID receiverId;
    @NotNull
    private UUID senderId;
    @NotBlank
    private String content;
    @NotBlank
    private String name;
    @NotBlank
    private String username;
}

