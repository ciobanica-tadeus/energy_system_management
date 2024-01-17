package com.example.chat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer id;

    @Column(name = "receiver_id", nullable = false)
    private UUID receiverId;

    @Column(name = "sender_id", nullable = false)
    private UUID senderId;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "seen", nullable = false)
    private Boolean seen;

    @Column(name = "message_external_id", nullable = false)
    private UUID messageExternalId;

    @PrePersist
    void prePersist() {
        if (messageExternalId == null) {
            messageExternalId = UUID.randomUUID();
        }
    }

}
