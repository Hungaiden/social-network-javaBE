package com.example.FakeBook.Mapper;

import com.example.FakeBook.DTO.Request.ChatPrivateMessageRequestDTO;
import com.example.FakeBook.DTO.Request.CreatePrivateConversationRequest;
import com.example.FakeBook.DTO.Response.MessageResponseDTO;
import com.example.FakeBook.Entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChatMapper {
    Message toMessageEntity(ChatPrivateMessageRequestDTO request);

    @Mapping(source = "conversation.id", target = "conversationId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.displayName", target = "senderName")
    MessageResponseDTO toMessageDTO(Message message);

    @Mapping(source = "conversation.id", target = "conversationId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.displayName", target = "senderName")
    List<MessageResponseDTO> toMessageDTOList(List<Message> messageList);
}
