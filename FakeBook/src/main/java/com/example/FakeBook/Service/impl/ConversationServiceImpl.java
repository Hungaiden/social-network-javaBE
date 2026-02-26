package com.example.FakeBook.Service.impl;

import com.example.FakeBook.DTO.Request.CreateGroupConversationRequest;
import com.example.FakeBook.DTO.Request.CreatePrivateConversationRequest;
import com.example.FakeBook.DTO.Response.*;
import com.example.FakeBook.Entity.*;
import com.example.FakeBook.Enums.ErrorCode;
import com.example.FakeBook.Exception.AppException;
import com.example.FakeBook.Mapper.ChatMapper;
import com.example.FakeBook.Mapper.ConversationMapper;
import com.example.FakeBook.Repository.ConversationMemberRepository;
import com.example.FakeBook.Repository.ConversationRepository;
import com.example.FakeBook.Repository.UserRepository;
import com.example.FakeBook.Service.AuthService;
import com.example.FakeBook.Service.ChatService;
import com.example.FakeBook.Service.ConversationMemberService;
import com.example.FakeBook.Service.ConversationService;
import com.sun.tools.jconsole.JConsoleContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.boot.autoconfigure.container.ContainerImageMetadata.isPresent;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationServiceImpl implements ConversationService {

    final AuthService authService;
    final ConversationMemberService conversationMemberService;
    final ChatService chatService;
    final ChatMapper chatMapper;
    final ConversationMapper conversationMapper;
    final ConversationMemberRepository conversationMemberRepository;
    final ConversationRepository conversationRepository;


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Conversation createPrivateConversation () {

        Conversation conversation = Conversation.builder()
                .type("PRIVATE")
                .build();

        Conversation savedConversation = conversationRepository.save(conversation);
        return savedConversation;
    }

    @Override
    public Optional<Conversation> findPrivateBetween (UUID userA, UUID userB) {
        return conversationRepository.findPrivateConversationBetweenTwoUsers(userA, userB);
    }

    @Override
    public Conversation refresh(Conversation conversation) {
        entityManager.refresh(conversation);
        return conversation;
    }

    @Override
    public boolean validateInputConversation (Set<UUID> memberIds) {
        // check so luong member toi thieu >= 3
        if (memberIds.size() < 3) {
            throw new AppException(ErrorCode.MINIMUM_MEMBERS_REQUIRED);
        }
        return true;
    }

    @Override
    public Conversation createGroupConversation (String name) {
        Conversation conversation = Conversation.builder()
                .type("GROUP")
                .name(name)
                .build();

        Conversation savedConversation = conversationRepository.save(conversation);
        return savedConversation;
    }

    @Override
    public Conversation findConversationById (UUID conversationID) {
        Conversation conversation = conversationRepository.findById(conversationID)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        return conversation;
    }

    @Override
    public boolean isAdmin (UUID conversationID, UUID userId) {
        return conversationMemberRepository
                .existsByConversationIdAndUserIdAndIsAdminTrue(conversationID, userId);
    }

    @Override
    public PagedResponse<ConversationResponse> findPrivateConversationByUserId (UUID userId, Pageable pageable) {

        Pageable sortPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Conversation> conversationPage = conversationRepository.findPrivateConversationByUserId(userId, sortPageable);

        List<Conversation> conversationList = conversationPage.getContent();
        List<ConversationResponse> conversationResponseList = new ArrayList<>();
        for(Conversation x : conversationList) {
            UUID conversationId = x.getId();
            // get last message
            MessageResponseDTO lastMessage = chatService.findLastMessage(conversationId);

            List<ConversationMember> conversationMember = conversationMemberService.findAllByConversationId(conversationId);
            for(ConversationMember c: conversationMember) {
                String name = c.getUser().getDisplayName();
                UUID memberId = c.getUser().getId();
                if(!memberId.equals(userId)) {
                    conversationResponseList.add(ConversationResponse.builder()
                            .id(conversationId)
                            .name(name)
                            .lastMessageContent(lastMessage != null ? lastMessage.getContent() : "")
                            .lastMessageTime(lastMessage != null ? lastMessage.getCreatedAt() : null)
                            .build()
                    );
                }
            }
        }

        return PagedResponse.<ConversationResponse>builder()
                .content(conversationResponseList)
                .page(conversationPage.getNumber())
                .size(conversationPage.getSize())
                .totalPages(conversationPage.getTotalPages())
                .totalElements(conversationPage.getTotalElements())
                .last(conversationPage.isLast())
                .build();
    }
}
