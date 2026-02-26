package com.example.FakeBook.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AddMembersResponse {
    private UUID conversationId;
    private List<UUID> addedUserIds;
}
