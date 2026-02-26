package com.example.FakeBook.Service.impl;

import com.example.FakeBook.DTO.Request.FriendRequestDTO;
import com.example.FakeBook.DTO.Response.FriendResponse;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.DTO.Response.UserResponseBase;
import com.example.FakeBook.Entity.Friend;
import com.example.FakeBook.Enums.ErrorCode;
import com.example.FakeBook.Exception.AppException;
import com.example.FakeBook.Mapper.Custom.FriendCustomMapper;
import com.example.FakeBook.Mapper.FriendMapper;
import com.example.FakeBook.Repository.FriendRepository;
import com.example.FakeBook.Service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final FriendCustomMapper friendCustomMapper;
    @Override
    public FriendResponse createFriend(FriendRequestDTO request) {
        UUID userA_Id = request.getUserA().getId(), userB_Id = request.getUserB().getId();
        if (friendRepository.existsByUserAIdAndUserBIdOrUserAIdAndUserBId(userA_Id, userB_Id, userB_Id, userA_Id)) throw new AppException(ErrorCode.FRIEND_ALREADY);

        Friend friend = Friend.builder().userA(request.getUserA()).userB(request.getUserB()).build();
        return friendMapper.toResponse(friendRepository.save(friend));
    }

    @Override
    public boolean isFriend(UUID userA_id, UUID userB_id) {
        return friendRepository.existsByUserAIdAndUserBIdOrUserAIdAndUserBId(userA_id, userB_id, userB_id, userA_id);
    }

    @Override
    public PagedResponse<UserResponseBase> searchAllUserFriend(UUID id, Pageable pageable) {
        if (pageable.getPageNumber() < 0) throw new AppException(ErrorCode.PAGE_INDEX_INVALID);
        if (pageable.getPageSize() <= 0) throw new AppException(ErrorCode.PAGE_SIZE_INVALID);

        Page<Friend> friendPage = friendRepository.findAllByUserAIdOrUserBId(id, id, pageable);
        return friendCustomMapper.toPageResponse(friendPage, id);
    }

    @Override
    public List<UserResponseBase> getAllUserFriend(UUID userId) {
        List<Friend> friendList = friendRepository.findAllByUserAIdOrUserBId(userId, userId);
        return friendCustomMapper.toListResponse(friendList, userId);
    }

    @Override
    public void deleteFriend(UUID userA_Id, UUID userB_Id) {
        if (!friendRepository.existsByUserAIdAndUserBIdOrUserAIdAndUserBId(userA_Id, userB_Id, userB_Id, userA_Id)) throw new AppException(ErrorCode.FRIEND_NOT_FOUND);
        friendRepository.deleteByUserAIdAndUserBIdOrUserBIdAndUserAId(userA_Id, userB_Id, userB_Id, userA_Id);
    }
}
