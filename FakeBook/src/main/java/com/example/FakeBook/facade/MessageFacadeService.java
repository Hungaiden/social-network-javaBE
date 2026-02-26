package com.example.FakeBook.facade;

import com.example.FakeBook.DTO.Response.ApiResponse;
import com.example.FakeBook.DTO.Response.MessageResponseDTO;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.Service.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageFacadeService {
    private final MessageService messageService;

    public PagedResponse<MessageResponseDTO> getAllMessageOfAConversation (UUID conversationID, Pageable pageable) {
        return messageService.getAllMessageOfAConversation(conversationID, pageable);
    }
}
