package com.example.chat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact")
@Builder

public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "user_external_id", nullable = false)
    private UUID userExternalId;

}
