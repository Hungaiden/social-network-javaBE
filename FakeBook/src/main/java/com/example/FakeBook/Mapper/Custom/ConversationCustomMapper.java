package com.example.FakeBook.Mapper.Custom;

import com.example.FakeBook.DTO.Response.ConversationResponse;
import com.example.FakeBook.DTO.Response.CreateGroupConversationResponse;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.Entity.Conversation;
import com.example.FakeBook.Entity.ConversationMember;
import com.example.FakeBook.Entity.User;
import com.example.FakeBook.Service.ConversationMemberService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ConversationCustomMapper {
    private final ConversationMemberService conversationMemberService;

    public ConversationCustomMapper(ConversationMemberService conversationMemberService) {
        this.conversationMemberService = conversationMemberService;
    }

    public CreateGroupConversationResponse toGroupConversationResponse(
            Conversation conversation,
            List<User> users,
            List<ConversationMember> conversationMembers) {
        Map<UUID, Boolean> adminMap = conversationMembers.stream()
                .collect(Collectors.toMap(
                        m -> m.getUser().getId(),
                        ConversationMember::getIsAdmin
                ));

        List<CreateGroupConversationResponse.MemberInfo> members = users.stream()
                .map(user -> CreateGroupConversationResponse.MemberInfo.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .isAdmin(adminMap.getOrDefault(user.getId(), false))
                        .build())
                .toList();


        // BƯỚC 3: Build response cuối cùng
        return CreateGroupConversationResponse.builder()
                .conversationId(conversation.getId())
                .name(conversation.getName())
                .type(conversation.getType())
                .members(members)
                .build();
    }

//    public PagedResponse<ConversationResponse> toListPrivateConversation (Page<Conversation> conversationPage) {
//        List<Conversation> conversationList = conversationPage.getContent();
//        List<ConversationResponse> conversationResponseList = new ArrayList<>();
//
//        for (Conversation x : conversationList) {
//            ConversationMember conversationMember = x.getMembers();
//        }
//    }
}
