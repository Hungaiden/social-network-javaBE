package com.example.FakeBook.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    UUID id;
    String title;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    // Thong tin chu bai viet
    UUID ownerId;
    String ownerUsername;
    String ownerDisplayName;
    String ownerAvatar;
}
