package com.example.FakeBook.DTO.Request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AddMembersRequest {
    @NotEmpty(message = "UserIds cannot be empty")
    private List<UUID> userIds;
}
