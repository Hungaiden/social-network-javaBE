package com.example.FakeBook.Controller;

import com.example.FakeBook.DTO.Response.ApiResponse;
import com.example.FakeBook.DTO.Response.MessageResponseDTO;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.DTO.Response.UserResponseBase;
import com.example.FakeBook.Entity.Message;
import com.example.FakeBook.facade.MessageFacadeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageController {

    private final MessageFacadeService messageFacadeService;

    @GetMapping("/{conversationId}")
    public ApiResponse<PagedResponse<MessageResponseDTO>> getAllMessageOfAUser (@PathVariable UUID conversationId, Pageable pageable) {
        return  ApiResponse.<PagedResponse<MessageResponseDTO>>builder()
                .message("ok")
                .Result(messageFacadeService.getAllMessageOfAConversation(conversationId, pageable))
                .build();
    }

}
