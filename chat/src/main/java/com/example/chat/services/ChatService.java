package com.example.chat.services;

import com.example.chat.entities.Contact;
import com.example.chat.entities.Message;
import com.example.chat.repositories.ContactsRepository;
import com.example.chat.repositories.MessageRepository;
import com.example.chat.services.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final NotificationService notificationService;
    private final MessageRepository messageRepository;
    private final ContactsRepository contactsRepository;

    public List<ChatContactDto> getContacts(UUID userId) {
        List<Message> allByReceiverId = messageRepository.findAllByReceiverId(userId);

        List<UUID> contactsList = new ArrayList<>();
        for (Message m : allByReceiverId) {
            if (!contactsList.contains(m.getSenderId())) {
                contactsList.add(m.getSenderId());
            }
        }

        List<ChatContactDto> contacts = new ArrayList<>();
        for (UUID id : contactsList) {
            Optional<Contact> contact = contactsRepository.findByUserExternalId(id);
            if (contact.isPresent()) {
                ChatContactDto chatContactDto = ChatContactDto
                        .builder()
                        .name(contact.get().getName())
                        .username(contact.get().getUsername())
                        .userExternalId(contact.get().getUserExternalId())
                        .build();
                contacts.add(chatContactDto);
            }
        }
        return contacts;
    }

    public List<ChatMessageDto> getConversation(GetChatInformationRequest getChatInformationRequest) {
        List<Message> messages = messageRepository.findConversation(
                getChatInformationRequest.getUserExternalId(),
                getChatInformationRequest.getContactExternalId()
        );
        List<ChatMessageDto> chatMessageDtos = new ArrayList<>();
        for (Message m : messages) {
            ChatMessageDto chatMessageDto = ChatMessageDto
                    .builder()
                    .senderId(m.getSenderId())
                    .receiverId(m.getReceiverId())
                    .content(m.getContent())
                    .sentAt(m.getSentAt())
                    .seen(m.getSeen())
                    .build();
            chatMessageDtos.add(chatMessageDto);
        }
        return chatMessageDtos;
    }

    public ResponseMessage saveMessage(SendMessageRequest sendMessageRequest) {
        checkIfSenderExists(sendMessageRequest);
        String message = sendMessageRequest.getName() + " is typing...";
        notificationService.sendPrivateMessages(sendMessageRequest.getReceiverId(), message);

        messageRepository.save(Message.builder()
                .receiverId(sendMessageRequest.getReceiverId())
                .senderId(sendMessageRequest.getSenderId())
                .content(sendMessageRequest.getContent())
                .seen(false)
                .sentAt(LocalDateTime.now())
                .build());

        return ResponseMessage.builder().message("Message sent!").build();
    }

    private void checkIfSenderExists(SendMessageRequest sendMessageRequest) {
        Optional<Contact> sender = contactsRepository.findByUserExternalId(sendMessageRequest.getSenderId());

        if (sender.isEmpty()) {
            contactsRepository.save(Contact.builder()
                    .username(sendMessageRequest.getUsername())
                    .name(sendMessageRequest.getName())
                    .userExternalId(sendMessageRequest.getSenderId())
                    .build());
        }
    }


    public ResponseMessage seeMessages(GetChatInformationRequest request) {
        int ok = 0;

        List<Message> messages = messageRepository.findAllByReceiverIdAndSenderIdOrderBySentAtAsc(
                request.getUserExternalId(),
                request.getContactExternalId()
        );
        if (messages.isEmpty()) {
            return ResponseMessage.builder().message("No messages to see!").build();
        }
        for (Message m : messages) {
            if (!m.getSeen()) {
                m.setSeen(true);
                messageRepository.save(m);
                ok = 1;
            }
        }
        if (ok == 1) {
            notificationService.sendPrivateMessages(request.getContactExternalId(), "Message seen!");
        }
        return ResponseMessage.builder().message("Messages seen!").build();
    }
}
