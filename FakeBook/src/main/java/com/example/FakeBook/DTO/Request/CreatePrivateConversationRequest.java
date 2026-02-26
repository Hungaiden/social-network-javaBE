package com.example.FakeBook.DTO.Request;

import lombok.Data;

import java.util.UUID;

@Data
public class CreatePrivateConversationRequest {
    private UUID targetUserId;
}
