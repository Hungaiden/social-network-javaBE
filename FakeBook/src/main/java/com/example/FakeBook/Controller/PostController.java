package com.example.FakeBook.Controller;

import com.example.FakeBook.DTO.Request.PostCreationRequest;
import com.example.FakeBook.DTO.Request.PostUpdateRequest;
import com.example.FakeBook.DTO.Response.ApiResponse;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.DTO.Response.PostResponse;
import com.example.FakeBook.facade.PostFacadeService;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/post")
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PostController {

    final PostFacadeService postFacadeService;

    @PostMapping
    public ApiResponse<PostResponse> createPost(@Valid @RequestBody PostCreationRequest request){
        PostResponse postResponse = postFacadeService.createPost(request);
        return ApiResponse.<PostResponse>builder()
                .code(1000)
                .message("Dang bai thanh cong")
                .Result(postResponse)
                .build();
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponse> updatePost(@PathVariable UUID postId,@Valid @RequestBody PostUpdateRequest request){
        PostResponse postResponse = postFacadeService.updatePost(postId, request);
        return ApiResponse.<PostResponse>builder()
                .code(1000)
                .message("Cap nhat bai viet thanh cong")
                .Result(postResponse)
                .build();
    }

    @DeleteMapping("/{postId}")
    public ApiResponse deletePost(@PathVariable UUID postId){
        postFacadeService.deletePostById(postId);
        return ApiResponse.builder()
                .code(1000)
                .message("Xoa bai viet thanh cong")
                .build();
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPostById(@PathVariable UUID postId){
        PostResponse postResponse = postFacadeService.getPostById(postId);
        return ApiResponse.<PostResponse>builder()
                .code(1000)
                .message("Tim kiem thanh cong")
                .Result(postResponse)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<PagedResponse<PostResponse>> searchAllPost(Pageable  pageable){
        PagedResponse<PostResponse> postResponsePage = postFacadeService.searchAllPost(pageable);
        return ApiResponse.<PagedResponse<PostResponse>>builder()
                .code(1000)
                .Result(postResponsePage)
                .build();
    }

    @GetMapping("/home")
    public ApiResponse<PagedResponse<PostResponse>> getNewsFeed(Pageable pageable){
        PagedResponse<PostResponse> feed = postFacadeService.getNewsFeed(pageable);
        return ApiResponse.<PagedResponse<PostResponse>>builder()
                .code(1000)
                .message("Lay bai viet trang home thanh cong")
                .Result(feed)
                .build();
    }
}
