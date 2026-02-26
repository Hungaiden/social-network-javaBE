package com.example.FakeBook.Service.impl;

import com.example.FakeBook.DTO.Request.ChatPrivateMessageRequestDTO;
import com.example.FakeBook.DTO.Response.MessageResponseDTO;
import com.example.FakeBook.Entity.Conversation;
import com.example.FakeBook.Entity.Message;
import com.example.FakeBook.Entity.User;
import com.example.FakeBook.Mapper.ChatMapper;
import com.example.FakeBook.Repository.ConversationRepository;
import com.example.FakeBook.Repository.MessageRepository;
import com.example.FakeBook.Repository.UserRepository;
import com.example.FakeBook.Service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    @Override
    public MessageResponseDTO saveMessage (ChatPrivateMessageRequestDTO request, Conversation conversation, User sender) {
        Message message = chatMapper.toMessageEntity(request);
        message.setConversation(conversation);
        message.setSender(sender);

        return chatMapper.toMessageDTO(messageRepository.save(message));
    }

    @Override
    public MessageResponseDTO findLastMessage (UUID conversationId) {
        Message message = messageRepository.findFirstByConversationIdOrderByCreatedAtDesc(conversationId);

        return chatMapper.toMessageDTO(message);
    }
}
