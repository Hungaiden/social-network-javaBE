package com.example.FakeBook.Controller;

import com.example.FakeBook.DTO.Request.RespondFriendRequest;
import com.example.FakeBook.DTO.Request.SendFriendRequest;
import com.example.FakeBook.DTO.Response.ApiResponse;
import com.example.FakeBook.DTO.Response.FriendRequestResponse;
import com.example.FakeBook.facade.FriendRequestFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friend-request")
public class FriendRequestController {
    private final FriendRequestFacadeService friendRequestFacadeService;

    @PostMapping()
    public ApiResponse<FriendRequestResponse> sendFriendRequest (@RequestBody SendFriendRequest request) {
        return ApiResponse.<FriendRequestResponse>builder()
                .code(1000)
                .message("ok")
                .Result(friendRequestFacadeService.sendFriendRequest(request))
                .build();
    }

    @PostMapping("/response")
    public ApiResponse<FriendRequestResponse> respondFriendRequest(@RequestBody RespondFriendRequest request) {
        return ApiResponse.<FriendRequestResponse>builder()
                .code(1000)
                .message("ok")
                .Result(friendRequestFacadeService.respondFriendRequest(request))
                .build();
    }

    @GetMapping("/myFriendRequest")
    public ApiResponse<List<FriendRequestResponse>> getMyFriendRequests() {
        return ApiResponse.<List<FriendRequestResponse>>builder()
                .code(1000)
                .message("ok")
                .Result(friendRequestFacadeService.getAllFriendRequest())
                .build();
    }

    @GetMapping("/status")
    public ApiResponse<String> hasPendingFriendRequest(@RequestParam UUID receiverId) {
        return ApiResponse.<String>builder()
                .code(1000)
                .message("ok")
                .Result(friendRequestFacadeService.getStatusFriendRequest(receiverId))
                .build();
    }
}
