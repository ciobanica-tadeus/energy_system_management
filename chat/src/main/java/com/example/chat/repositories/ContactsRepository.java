package com.example.chat.repositories;

import com.example.chat.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactsRepository extends JpaRepository<Contact, Integer> {
    Optional<Contact> findByUserExternalId(UUID userExternalId);
}
