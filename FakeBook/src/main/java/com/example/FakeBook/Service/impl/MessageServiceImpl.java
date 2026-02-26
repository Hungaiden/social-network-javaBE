package com.example.FakeBook.Service.impl;

import com.example.FakeBook.DTO.Response.MessageResponseDTO;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.Entity.Message;
import com.example.FakeBook.Mapper.ChatMapper;
import com.example.FakeBook.Repository.MessageRepository;
import com.example.FakeBook.Service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatMapper chatMapper;

    @Override
    public PagedResponse<MessageResponseDTO> getAllMessageOfAConversation (UUID conversationID, Pageable pageable) {
        Page<Message> messageList = messageRepository.findAllByConversationId(conversationID, pageable);
        return PagedResponse.<MessageResponseDTO>builder()
                .content(chatMapper.toMessageDTOList(messageList.getContent()))
                .page(messageList.getNumber())
                .size(messageList.getSize())
                .totalPages(messageList.getTotalPages())
                .totalElements(messageList.getTotalElements())
                .last(messageList.isLast())
                .build();
    }
}
