    package com.example.FakeBook.DTO.Request;

    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import lombok.Data;

    import java.util.UUID;

    @Data
    public class ChatPrivateMessageRequestDTO {
        @NotNull
        private UUID conversationId;

        @NotNull
        private UUID senderId;

        @NotBlank
        private String content;
    }
