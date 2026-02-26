package com.example.FakeBook.Service.impl;

import com.example.FakeBook.DTO.Request.PostCreationRequest;
import com.example.FakeBook.DTO.Request.PostUpdateRequest;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.DTO.Response.PostResponse;
import com.example.FakeBook.Entity.Post;
import com.example.FakeBook.Entity.User;
import com.example.FakeBook.Enums.ErrorCode;
import com.example.FakeBook.Exception.AppException;
import com.example.FakeBook.Mapper.PostMapper;
import com.example.FakeBook.Repository.PostRepository;
import com.example.FakeBook.Service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PostServiceImpl implements PostService {

    PostRepository postRepository;

    PostMapper postMapper;

    //Dang bai viet
    @Override
    public PostResponse createPost(PostCreationRequest request, User owner){

        Post post = postMapper.toEntity(request);

        post.setOwner(owner);

        return postMapper.toResponse(postRepository.save(post));
    }

    //Cap nhat bai viet
    @Override
    public PostResponse updatePost(PostUpdateRequest request, Post post){

        postMapper.updatePostFromDto(request,post);

        return postMapper.toResponse(postRepository.save(post));
    }

    //xoa 1 bai viet
    @Override
    public void deletePostById(UUID postId){
        postRepository.deleteById(postId);
    }

    @Override
    public PostResponse getPostById(Post post){

        return postMapper.toResponse(post);
    }

    //Lay tat ca bai viet
    @Override
    public PagedResponse<PostResponse> searchAllPost(Pageable  pageable){
        Page<Post> postPage = postRepository.findAll(pageable);
        return PagedResponse.<PostResponse>builder()
                .content(postMapper.toResponseList(postPage.getContent()))
                .page(postPage.getNumber())
                .size(postPage.getSize())
                .totalPages(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .last(postPage.isLast())
                .build();
    }

    //Lay tat ca bai viet cua 1user
    @Override
    public  PagedResponse<PostResponse> getPostByUserId(Pageable pageable, UUID ownerId){

        Pageable sortPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Post> postPage = postRepository.findByOwnerId(ownerId, sortPageable);

        return PagedResponse.<PostResponse>builder()
                .content(postMapper.toResponseList(postPage.getContent()))
                .page(postPage.getNumber())
                .size(postPage.getSize())
                .totalPages(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .last(postPage.isLast())
                .build();


    }

    //check bai viet ton tai khong
    @Override
    public Post checkPostById(UUID postId){
        return postRepository.findById(postId).orElseThrow(()-> new AppException(ErrorCode.POST_NOT_FOUND));
    }

    //Lay bai viet trang home (news feed) - ban than + ban be, sap xep moi nhat
    @Override
    public PagedResponse<PostResponse> getNewsFeed(List<UUID> userIds, Pageable pageable) {
        Pageable sortPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> postPage = postRepository.findByOwnerIdIn(userIds, sortPageable);
        return PagedResponse.<PostResponse>builder()
                .content(postMapper.toResponseList(postPage.getContent()))
                .page(postPage.getNumber())
                .size(postPage.getSize())
                .totalPages(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .last(postPage.isLast())
                .build();
    }
}
