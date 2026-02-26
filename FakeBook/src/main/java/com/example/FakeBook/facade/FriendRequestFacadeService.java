package com.example.FakeBook.facade;

import com.example.FakeBook.DTO.Request.FriendRequestDTO;
import com.example.FakeBook.DTO.Request.RespondFriendRequest;
import com.example.FakeBook.DTO.Request.SendFriendRequest;
import com.example.FakeBook.DTO.Response.FriendRequestResponse;
import com.example.FakeBook.Entity.FriendRequest;

import com.example.FakeBook.Entity.User;
import com.example.FakeBook.Enums.ErrorCode;
import com.example.FakeBook.Exception.AppException;
import com.example.FakeBook.Service.AuthService;
import com.example.FakeBook.Service.FriendRequestService;
import com.example.FakeBook.Service.FriendService;
import com.example.FakeBook.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class FriendRequestFacadeService {
    private final FriendRequestService friendRequestService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private final FriendService friendService;
    private final AuthService authService;

    // Gui loi moi ket ban
    public FriendRequestResponse sendFriendRequest (SendFriendRequest request) {
        UUID senderId = UUID.fromString(authService.getCurrentUserId());

         FriendRequest friendRequest = friendRequestService.checkFriendRequest(senderId, request.getReceiverId());

        // Tao ban ghi moi neu chua ton tai
        if (friendRequest == null) {
            friendRequest = FriendRequest.builder()
                .sender(userService.findUser(senderId))
                .receiver(userService.findUser(request.getReceiverId()))
                .status(FriendRequest.Status.PENDING)
                .message(request.getMessage())
                .build();
        } else {
            friendRequest.setSender(userService.findUser(senderId));
            friendRequest.setReceiver(userService.findUser(request.getReceiverId()));
            friendRequest.setStatus(FriendRequest.Status.PENDING);
            friendRequest.setMessage(request.getMessage());
        }

        // Save vao database
        FriendRequestResponse friendRequestResponse = friendRequestService.saveFriendRequest(friendRequest);

        // WebSocket day thong bao
        simpMessagingTemplate.convertAndSendToUser(
                request.getReceiverId().toString(),
                "/queue/friend-request",
                "Bạn có một lời mời kết bạn mới!"
        );

        return friendRequestResponse;
    }

    public FriendRequestResponse respondFriendRequest (RespondFriendRequest request) {
        UUID receiverId = UUID.fromString(authService.getCurrentUserId());
        String nameReceiver = authService.getCurrentUserDisplayName();

        // Validate: receiverId giong voi id nguoi dang call api
        if (!receiverId.equals(request.getReceiverId())) throw new AppException(ErrorCode.UNAUTHORIZED);

        // Lay ban ghi tu database
        FriendRequest friendRequest = friendRequestService.findFriendRequestById(request.getRequestId());
        User userA = userService.findUser(friendRequest.getSender().getId());
        User userB = userService.findUser(friendRequest.getReceiver().getId());

        //Xu ly 2 truong hop AC va REJECT
        boolean accepted = request.getAction() == RespondFriendRequest.Action.ACCEPT;
        if (accepted) {
            friendService.createFriend(FriendRequestDTO.builder().userA(userA).userB(userB).build());
            friendRequest.setStatus(FriendRequest.Status.ACCEPTED);
        }else {
            friendRequest.setStatus(FriendRequest.Status.REJECTED);
        }
        FriendRequestResponse friendRequestResponse = friendRequestService.saveFriendRequest(friendRequest);

        // Tao chuoi de gui thong bao
        String msg = accepted ? "Lời mời của bạn đã được chấp nhận bởi " : "Lời mời của bạn đã bị từ chối bởi ";
        UUID senderId = userA.getId();

        simpMessagingTemplate.convertAndSendToUser(
                senderId.toString(),
                "/queue/friend-response",
                msg + nameReceiver
        );

        return friendRequestResponse;
    }

    public List<FriendRequestResponse> getAllFriendRequest() {
        UUID receiverId = UUID.fromString(authService.getCurrentUserId());
        return friendRequestService.getAllFriendRequest(receiverId);
    }

    public String getStatusFriendRequest(UUID receiverId) {
        UUID senderId = UUID.fromString(authService.getCurrentUserId());
        return friendRequestService.getStatusFriendRequest(senderId, receiverId);
    }

}
