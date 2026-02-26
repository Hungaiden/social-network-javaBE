package com.example.FakeBook.Controller;

import com.example.FakeBook.DTO.Request.AddMembersRequest;
import com.example.FakeBook.DTO.Request.CreateGroupConversationRequest;
import com.example.FakeBook.DTO.Request.CreatePrivateConversationRequest;
import com.example.FakeBook.DTO.Response.*;
import com.example.FakeBook.Entity.Conversation;
import com.example.FakeBook.Service.ConversationService;
import com.example.FakeBook.facade.ConversationFacadeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/conversation")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationController {
    final ConversationFacadeService conversationFacadeService;

    @PostMapping("/private")
    public ApiResponse<CreatePrivateConversationResponse> createPrivateConversation(@RequestBody CreatePrivateConversationRequest createPrivateConversationRequest) {
        return ApiResponse.<CreatePrivateConversationResponse>builder()
                .code(1000)
                .Result(conversationFacadeService.createPrivateConversationResponse(createPrivateConversationRequest))
                .message("Complete create private conversation")
                .build();
    }

    @PostMapping("/group")
    public ApiResponse<CreateGroupConversationResponse> createGroupConversationResponseApiResponse(@RequestBody CreateGroupConversationRequest createGroupConversationRequest) {
        return ApiResponse.<CreateGroupConversationResponse>builder()
                .code(1000)
                .Result(conversationFacadeService.createGroupConversation(createGroupConversationRequest))
                .message("Complete create group conversation")
                .build();
    }

    @PostMapping("/{conversationId}/members")
    public ApiResponse<AddMembersResponse> addMembers (@PathVariable UUID conversationId, @Valid @RequestBody AddMembersRequest request) {
        return ApiResponse.<AddMembersResponse>builder()
                .code(1000)
                .Result(conversationFacadeService.addMembers(conversationId, request))
                .message("Complete add member to" + conversationId)
                .build();
    }

    @GetMapping("/by-user/private")
    public ApiResponse<PagedResponse<ConversationResponse>> getAllPrivateConversations (Pageable pageable) {

        PagedResponse<ConversationResponse> pagedResponse = conversationFacadeService.getAllConversations(pageable);
        return ApiResponse.<PagedResponse<ConversationResponse>>builder()
                .code(1000)
                .Result(pagedResponse)
                .message("Get conversations successfully")
                .build();
    }
}
