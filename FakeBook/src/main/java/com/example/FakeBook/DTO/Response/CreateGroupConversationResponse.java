package com.example.FakeBook.DTO.Response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CreateGroupConversationResponse {

    private UUID conversationId;

    private String name;

    private String type;

    private List<MemberInfo> members;

    @Data
    @Builder
    public static class MemberInfo {

        private UUID userId;

        private String username;

        private Boolean isAdmin;
    }
}
