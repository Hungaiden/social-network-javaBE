package com.example.FakeBook.facade;

import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.DTO.Response.UserResponseBase;
import com.example.FakeBook.Entity.FriendRequest;
import com.example.FakeBook.Service.AuthService;
import com.example.FakeBook.Service.FriendRequestService;
import com.example.FakeBook.Service.FriendService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FriendFacadeService {
    private final FriendService friendService;
    private final AuthService authService;
    private final FriendRequestService friendRequestService;
    private final SimpMessagingTemplate simpMessagingTemplate;

//    public boolean isFriend(UUID userA_id, UUID userB_id) {
//        return friendService.isFriend(userA_id, userB_id);
//    }
    public PagedResponse<UserResponseBase> searchAllFriendUser(Pageable pageable) {
        UUID userId = UUID.fromString(authService.getCurrentUserId());
        return  friendService.searchAllUserFriend(userId, pageable);
    }

    @Transactional
    public void deleteFriend(UUID friendId) {
        UUID userId = UUID.fromString(authService.getCurrentUserId());
        String userDisplayName = authService.getCurrentUserDisplayName();

        //Lay ra ban ghi friendRequest tuong ung
        FriendRequest fr = friendRequestService.findFriendRequestBySenderIdAndReceiverId(userId, friendId);

        // Xoa ban ghi o bang Friend
        friendService.deleteFriend(userId, friendId);
        // Xu ly ben bang FriendRequest
        fr.setStatus(FriendRequest.Status.REJECTED);
        friendRequestService.saveFriendRequest(fr);

        simpMessagingTemplate.convertAndSendToUser(
                friendId.toString(),
                "queue/friend-delete",
                userDisplayName + " da huy ket ban voi ban!"
        );
    }
}
