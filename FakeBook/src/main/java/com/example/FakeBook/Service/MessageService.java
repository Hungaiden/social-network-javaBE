package com.example.FakeBook.Service;

import com.example.FakeBook.DTO.Response.MessageResponseDTO;
import com.example.FakeBook.DTO.Response.PagedResponse;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface MessageService {
    public PagedResponse<MessageResponseDTO> getAllMessageOfAConversation (UUID conversationID, Pageable pageable);
}
