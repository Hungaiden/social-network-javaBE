package com.example.FakeBook.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateGroupConversationRequest {

    @NotBlank(message = "Group name is required")
    @Size(min = 1, max = 100)
    private String name;

    @NotEmpty(message = "Members list cannot be empty")
    @Size(min = 2, message = "Group must have at least 2 members")
    private List<UUID> memberIds;
}
