package com.example.chat.services.dtos;

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
public class GetChatInformationRequest {
    @NotNull
    private UUID userExternalId;
    @NotNull
    private UUID contactExternalId;
}
