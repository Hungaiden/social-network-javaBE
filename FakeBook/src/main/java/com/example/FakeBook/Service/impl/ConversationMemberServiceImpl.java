package com.example.FakeBook.Service.impl;

import com.example.FakeBook.Entity.Conversation;
import com.example.FakeBook.Entity.ConversationMember;
import com.example.FakeBook.Entity.User;
import com.example.FakeBook.Repository.ConversationMemberRepository;
import com.example.FakeBook.Service.ConversationMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConversationMemberServiceImpl implements ConversationMemberService {

    private final ConversationMemberRepository conversationMemberRepository;

    @Override
    public void addMember(Conversation conversation, User user) {
        ConversationMember member = ConversationMember.builder()
                .conversation(conversation)
                .user(user)
                .build();
        conversationMemberRepository.save(member);
    }

    @Override
    public void addMember(Conversation conversation, User user, Boolean isAdmin) {
        ConversationMember member = ConversationMember.builder()
                .conversation(conversation)
                .user(user)
                .isAdmin(isAdmin)
                .build();

        conversationMemberRepository.save(member);
    }

    @Override
    public boolean exists (Conversation conversation, User user) {
        return conversationMemberRepository.existsByConversationAndUser(conversation, user);
    }

    @Override
    public List<ConversationMember> findAllByConversationId (UUID conversationId) {
        return conversationMemberRepository.findAllByConversationId(conversationId);
    }
}
