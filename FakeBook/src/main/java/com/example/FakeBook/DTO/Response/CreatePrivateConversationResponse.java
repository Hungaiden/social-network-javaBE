package com.example.FakeBook.DTO.Response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePrivateConversationResponse {
    UUID conversationId;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
