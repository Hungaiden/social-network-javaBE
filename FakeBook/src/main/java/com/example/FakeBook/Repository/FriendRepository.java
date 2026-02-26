package com.example.FakeBook.Repository;

import com.example.FakeBook.DTO.Request.FriendRequestDTO;
import com.example.FakeBook.Entity.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendRepository extends JpaRepository<Friend, UUID> {
    boolean existsByUserAIdAndUserBIdOrUserAIdAndUserBId(UUID userAId, UUID userBId, UUID userBId2, UUID userAId2);
    Page<Friend> findAllByUserAIdOrUserBId(UUID userAId, UUID userBId, Pageable pageable);
    List<Friend> findAllByUserAIdOrUserBId(UUID userAId, UUID userBId);
    void deleteByUserAIdAndUserBIdOrUserBIdAndUserAId(UUID userAId, UUID userBId, UUID userBId2, UUID userAId2);
}
