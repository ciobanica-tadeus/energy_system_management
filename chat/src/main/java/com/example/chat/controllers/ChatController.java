package com.example.chat.controllers;

import com.example.chat.services.ChatService;
import com.example.chat.services.dtos.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping(value = "/contacts/{userExternalId}")
    public ResponseEntity<List<ChatContactDto>> getContacts(@PathVariable UUID userExternalId){
        return ResponseEntity.ok(chatService.getContacts(userExternalId));
    }


    @PostMapping(value = "/messages")
    public ResponseEntity<List<ChatMessageDto>> getConversation(@RequestBody GetChatInformationRequest getChatInformationRequest){
        return ResponseEntity.ok(chatService.getConversation(getChatInformationRequest));
    }

    @PostMapping(value = "/send")
    public ResponseEntity<ResponseMessage> sendMessage(@RequestBody SendMessageRequest sendMessageRequest){
        return ResponseEntity.ok(chatService.saveMessage(sendMessageRequest));
    }

    @PostMapping(path = "/seen")
    public ResponseEntity<ResponseMessage> seeMessages(@RequestBody GetChatInformationRequest request){
        return ResponseEntity.ok(chatService.seeMessages(request));
    }

}
