package com.example.FakeBook.Service;

import com.example.FakeBook.DTO.Request.FriendRequestDTO;
import com.example.FakeBook.DTO.Response.FriendResponse;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.DTO.Response.UserResponseBase;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface FriendService {
    FriendResponse createFriend(FriendRequestDTO request);
    boolean isFriend(UUID userA_Id, UUID userB_id);
    PagedResponse<UserResponseBase> searchAllUserFriend(UUID id, Pageable pageable);
    List<UserResponseBase> getAllUserFriend(UUID userId);
    void deleteFriend(UUID userA_Id, UUID userB_Id);
}
