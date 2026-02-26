package com.example.FakeBook.Service;

import com.example.FakeBook.DTO.Request.PostCreationRequest;
import com.example.FakeBook.DTO.Request.PostUpdateRequest;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.DTO.Response.PostResponse;
import com.example.FakeBook.Entity.Post;
import com.example.FakeBook.Entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponse createPost(PostCreationRequest request, User owner);
    PagedResponse<PostResponse> searchAllPost(Pageable pageable);
    PagedResponse<PostResponse> getPostByUserId(Pageable pageable, UUID ownerId);
    PagedResponse<PostResponse> getNewsFeed(List<UUID> userIds, Pageable pageable);
    Post checkPostById(UUID postId);
    PostResponse getPostById(Post post);
    PostResponse updatePost(PostUpdateRequest request,  Post post);
    void deletePostById(UUID postId);
}
