package com.example.FakeBook.Mapper.Custom;

import com.example.FakeBook.DTO.Response.FriendRequestResponse;
import com.example.FakeBook.Entity.FriendRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendRequestCustomMapper {
    public FriendRequestResponse toResponse(FriendRequest friendRequest) {
        if (friendRequest == null) return null;

        return FriendRequestResponse.builder()
                .requestId(friendRequest.getId())
                .senderId(friendRequest.getSender().getId())
                .receiverDisplayName(friendRequest.getReceiver().getDisplayName())
                .receiverId(friendRequest.getReceiver().getId())
                .senderDisplayName(friendRequest.getSender().getDisplayName())
                .status(friendRequest.getStatus().toString())
                .message(friendRequest.getMessage())
                .created_at(friendRequest.getCreatedAt())
                .build();
    }

    public List<FriendRequestResponse> toResponseList(List<FriendRequest> friendRequests) {
        if (friendRequests == null) return null;

        return friendRequests.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
