package com.example.FakeBook.Service;

import com.example.FakeBook.DTO.Request.RespondFriendRequest;
import com.example.FakeBook.DTO.Request.SendFriendRequest;
import com.example.FakeBook.DTO.Response.FriendRequestResponse;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.Entity.FriendRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface FriendRequestService {
    FriendRequestResponse saveFriendRequest(FriendRequest friendRequest);
    FriendRequest findFriendRequestById(UUID Id);
    FriendRequest checkFriendRequest(UUID senderId, UUID receiverId);
//    PagedResponse<FriendRequest> findFriendRequestByReceiverIdAndStatus(UUID receiverId, Pageable pageable);
    FriendRequest findFriendRequestBySenderIdAndReceiverId(UUID senderId, UUID receiverId);

    List<FriendRequestResponse> getAllFriendRequest(UUID receiverId);

    String getStatusFriendRequest(UUID senderId, UUID receiverId);

}
