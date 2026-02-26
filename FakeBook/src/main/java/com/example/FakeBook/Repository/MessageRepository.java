package com.example.FakeBook.Repository;

import com.example.FakeBook.Entity.Message;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface MessageRepository  extends JpaRepository<Message, UUID> {
    Message findFirstByConversationIdOrderByCreatedAtDesc(UUID conversationId);

    Page<Message> findAllByConversationId(UUID conversationID, Pageable pageable);
}
