package com.example.FakeBook.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversationResponse {
    private UUID id;

    private String name;

    private String lastMessageContent;

    private LocalDateTime lastMessageTime;
}
