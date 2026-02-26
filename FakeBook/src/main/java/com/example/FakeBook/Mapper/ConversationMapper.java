package com.example.FakeBook.Mapper;

import com.example.FakeBook.DTO.Request.CreatePrivateConversationRequest;
import com.example.FakeBook.DTO.Response.ConversationResponse;
import com.example.FakeBook.DTO.Response.CreateGroupConversationResponse;
import com.example.FakeBook.DTO.Response.CreatePrivateConversationResponse;
import com.example.FakeBook.Entity.Conversation;
import com.example.FakeBook.Entity.ConversationMember;
import com.example.FakeBook.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConversationMapper {

    Conversation toEntity(CreatePrivateConversationRequest request);

    @Mapping(source = "id", target = "conversationId")
    CreatePrivateConversationResponse toPrivateConversationResponse(Conversation conversation);

    ConversationResponse toConversationResponse (Conversation conversation);

    List<ConversationResponse> toListConversationResponse (List<Conversation> conversations);
}
