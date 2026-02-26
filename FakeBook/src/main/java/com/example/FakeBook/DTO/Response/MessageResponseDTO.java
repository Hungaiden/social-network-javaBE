package com.example.FakeBook.DTO.Response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageResponseDTO {
    private UUID id;
    private UUID conversationId;
    private UUID senderId;

    private String senderName;    // optional
    private String content;

    private LocalDateTime createdAt;
}
