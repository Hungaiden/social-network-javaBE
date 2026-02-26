package com.example.FakeBook.Repository;

import com.example.FakeBook.Entity.FriendRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, UUID> {
    Page<FriendRequest> findByReceiverIdAndStatus(UUID receiverId, FriendRequest.Status status, Pageable pageable);
    Optional<FriendRequest> findFirstBySenderIdAndReceiverIdOrSenderIdAndReceiverId(UUID senderId1, UUID receiverId1, UUID senderId2, UUID receiverId2);

    List<FriendRequest> findByReceiverId(UUID receiverId);
}
