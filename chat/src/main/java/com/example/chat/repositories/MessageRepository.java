package com.example.chat.repositories;

import com.example.chat.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value = "SELECT * FROM MESSAGE WHERE receiver_id = :receiverId AND sender_id = :senderId " +
            "OR receiver_id = :senderId AND sender_id = :receiverId ORDER BY sent_at", nativeQuery = true)
    List<Message> findConversation(UUID receiverId, UUID senderId);
    List<Message> findAllByReceiverIdAndSenderIdOrderBySentAtAsc(UUID receiverId, UUID senderId);
    List<Message> findAllByReceiverId(UUID receiverId);

}
