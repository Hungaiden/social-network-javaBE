package com.example.FakeBook.Repository;

import com.example.FakeBook.Entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    @Query(value = "SELECT c FROM Conversation c " +
            "WHERE c.type = 'PRIVATE' " +
            "AND SIZE(c.members) = 2 " +
            "AND EXISTS (SELECT 1 FROM ConversationMember cm1 WHERE cm1.conversation = c AND cm1.user.id = :userId1) " +
            "AND EXISTS (SELECT 1 FROM ConversationMember cm2 WHERE cm2.conversation = c AND cm2.user.id = :userId2)")
    Optional<Conversation> findPrivateConversationBetweenTwoUsers(
            @Param("userId1") UUID userId1,
            @Param("userId2") UUID userId2
    );

    Optional<Conversation> findConversationById (UUID conversationId);

    @Query("""
        SELECT DISTINCT c
        FROM Conversation c
        JOIN c.members m
        WHERE m.user.id = :userId
        AND c.type = 'PRIVATE'
    """)
    Page<Conversation> findPrivateConversationByUserId (UUID userId, Pageable pageable);
}
