package com.example.FakeBook.Service;

import com.example.FakeBook.Entity.Conversation;
import com.example.FakeBook.Entity.ConversationMember;
import com.example.FakeBook.Entity.User;

import java.util.List;
import java.util.UUID;

public interface ConversationMemberService {

    void addMember(Conversation conversation, User user);

    void addMember(Conversation conversation, User user, Boolean isAdmin);

    boolean exists (Conversation conversation, User user);

    List<ConversationMember> findAllByConversationId (UUID conversationId);
}
