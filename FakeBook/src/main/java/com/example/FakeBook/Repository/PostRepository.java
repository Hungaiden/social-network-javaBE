package com.example.FakeBook.Repository;

import com.example.FakeBook.Entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Page<Post> findByOwnerId(UUID ownerId, Pageable pageable);
    Page<Post> findByOwnerIdIn(Collection<UUID> ownerIds, Pageable pageable);
}
