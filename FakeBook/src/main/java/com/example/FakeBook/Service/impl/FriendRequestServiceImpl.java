package com.example.FakeBook.Service.impl;


import com.example.FakeBook.DTO.Response.FriendRequestResponse;
import com.example.FakeBook.Entity.FriendRequest;
import com.example.FakeBook.Enums.ErrorCode;
import com.example.FakeBook.Exception.AppException;

import com.example.FakeBook.Mapper.Custom.FriendRequestCustomMapper;
import com.example.FakeBook.Mapper.FriendRequestMapper;
import com.example.FakeBook.Repository.FriendRequestRepository;
import com.example.FakeBook.Service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRequestMapper friendRequestMapper;
    private final FriendRequestCustomMapper friendRequestCustomMapper;

    @Override
    public FriendRequestResponse saveFriendRequest(FriendRequest friendRequest) {
        return friendRequestCustomMapper.toResponse(friendRequestRepository.save(friendRequest));
    }


    @Override
    public FriendRequest findFriendRequestById(UUID Id) {
        return friendRequestRepository.findById(Id).orElseThrow(() -> new AppException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));
    }

    @Override
    public FriendRequest checkFriendRequest(UUID senderId, UUID receiverId) {
        if (senderId.equals(receiverId)) throw new AppException(ErrorCode.ID_DUPLICATED);
        if (receiverId == null) throw new AppException(ErrorCode.ID_NULL);
        FriendRequest fr = friendRequestRepository.findFirstBySenderIdAndReceiverIdOrSenderIdAndReceiverId(senderId, receiverId, receiverId, senderId).orElse(null);
        if (fr == null || fr.getStatus() == FriendRequest.Status.REJECTED) return fr;
        //Truong hop da co yeu cau ket ban truoc do
        if (fr.getStatus() == FriendRequest.Status.PENDING) throw new AppException(ErrorCode.FRIEND_REQUEST_EXIST);
        //Truong hop da la ban be
        throw new AppException(ErrorCode.FRIEND_ALREADY);
    }

    @Override
    public FriendRequest findFriendRequestBySenderIdAndReceiverId(UUID senderId, UUID receiverId) {
        if (senderId.equals(receiverId)) throw new AppException(ErrorCode.ID_DUPLICATED);
        if (receiverId == null) throw new AppException(ErrorCode.ID_NULL);
        FriendRequest fr = friendRequestRepository.findFirstBySenderIdAndReceiverIdOrSenderIdAndReceiverId(senderId, receiverId, receiverId, senderId).orElse(null);
        if (fr.getStatus() == FriendRequest.Status.ACCEPTED)  return fr;
        //Truong hop khong phai ban be
        throw new AppException(ErrorCode.FRIEND_NOT_FOUND);
    }

    @Override
    public List<FriendRequestResponse> getAllFriendRequest(UUID receiverId) {
        List<FriendRequest> friendRequestList = friendRequestRepository.findByReceiverId(receiverId).stream().filter(fr -> fr.getStatus() == FriendRequest.Status.PENDING).toList();
        return friendRequestCustomMapper.toResponseList(friendRequestList);
    }

    @Override
    public String getStatusFriendRequest(UUID senderId, UUID receiverId) {
        if (senderId.equals(receiverId)) throw new AppException(ErrorCode.ID_DUPLICATED);
        if (receiverId == null) throw new AppException(ErrorCode.ID_NULL);
        FriendRequest fr = friendRequestRepository.findFirstBySenderIdAndReceiverIdOrSenderIdAndReceiverId(senderId, receiverId, receiverId, senderId).orElse(null);
        if (fr == null) throw new AppException(ErrorCode.FRIEND_REQUEST_NOT_FOUND);
        return fr.getStatus().toString();
    }

}
