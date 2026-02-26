package com.example.FakeBook.Service;

import com.example.FakeBook.DTO.Request.ChatPrivateMessageRequestDTO;
import com.example.FakeBook.DTO.Response.MessageResponseDTO;
import com.example.FakeBook.Entity.Conversation;
import com.example.FakeBook.Entity.Message;
import com.example.FakeBook.Entity.User;

import java.util.UUID;

public interface ChatService {
    public MessageResponseDTO saveMessage (ChatPrivateMessageRequestDTO request, Conversation conversation, User sender);

    public MessageResponseDTO findLastMessage (UUID conversationId);
}
