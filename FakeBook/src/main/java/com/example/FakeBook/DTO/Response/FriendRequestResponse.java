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
public class FriendRequestResponse {
    private UUID requestId;
    private UUID senderId;
    private String senderDisplayName;
    private UUID receiverId;
    private String receiverDisplayName;
    private String status;
    private String message;
    private LocalDateTime created_at;
}
