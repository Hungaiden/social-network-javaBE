package com.example.FakeBook.Repository;

import com.example.FakeBook.Entity.Conversation;
import com.example.FakeBook.Entity.ConversationMember;
import com.example.FakeBook.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConversationMemberRepository extends JpaRepository<ConversationMember, UUID> {

    boolean existsByConversationIdAndUserIdAndIsAdminTrue(UUID conversationID, UUID userId);

    boolean existsByConversationAndUser(Conversation conversation, User user);

    List<ConversationMember> findAllByConversationId (UUID conversationId);
}
