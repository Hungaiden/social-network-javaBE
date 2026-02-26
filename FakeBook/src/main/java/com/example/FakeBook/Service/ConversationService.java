package com.example.FakeBook.Service;

import com.example.FakeBook.DTO.Request.CreatePrivateConversationRequest;
import com.example.FakeBook.DTO.Response.ConversationResponse;
import com.example.FakeBook.DTO.Response.CreatePrivateConversationResponse;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.Entity.Conversation;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ConversationService {

    public Conversation createPrivateConversation ();

    public Optional<Conversation> findPrivateBetween (UUID userA, UUID userB);

    public Conversation refresh (Conversation conversation);

    public boolean validateInputConversation (Set<UUID> memberIds);

    public Conversation createGroupConversation (String name);

    public Conversation findConversationById (UUID conversationID);

    public boolean isAdmin (UUID conversationID, UUID userId);

    public PagedResponse<ConversationResponse> findPrivateConversationByUserId (UUID userId, Pageable pageable);
}
