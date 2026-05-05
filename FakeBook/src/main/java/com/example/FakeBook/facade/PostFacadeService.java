package com.example.FakeBook.facade;

import com.example.FakeBook.DTO.Request.PostCreationRequest;
import com.example.FakeBook.DTO.Request.PostUpdateRequest;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.DTO.Response.PostResponse;
import com.example.FakeBook.DTO.Response.UserResponseBase;
import com.example.FakeBook.Entity.Post;
import com.example.FakeBook.Entity.User;
import com.example.FakeBook.Enums.ErrorCode;
import com.example.FakeBook.Exception.AppException;
import com.example.FakeBook.Mapper.PostMapper;
import com.example.FakeBook.Service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostFacadeService {

    final PostService postService;
    final UserService userService;
    final FriendService friendService;
    final PostMapper postMapper;
    final PostRateLimitService postRateLimitService;
    final AuthService authService;
    final SimpMessagingTemplate simpMessagingTemplate;

    //Dang 1 bai viet
    public PostResponse createPost(PostCreationRequest request) {
        UUID userId = UUID.fromString(authService.getCurrentUserId());

        postRateLimitService.validateCanCreatePost(userId);

        User owner = userService.findUser(userId);

        PostResponse postResponse = postService.createPost(request, owner);

        //lay danh sach ban be
        List<UserResponseBase> friends = friendService.getAllUserFriend(userId);

        if(friends == null || friends.isEmpty()){
            return postResponse;
        }

        //lay danh sach id cua tung ban be
        List<UUID> friendIds = friends.stream().map(UserResponseBase::getUserId).toList();

        String msg = authService.getCurrentUserDisplayName() + " vừa đăng một bài viết mới!";

        for(UUID friendId : friendIds){
            simpMessagingTemplate.convertAndSendToUser(
                friendId.toString(),
                    "/queue/new-post",
                    msg
            );
        }

        return postResponse;
    }

    //Cap nhat 1 bai viet
    public PostResponse updatePost(UUID postId,PostUpdateRequest request) {
        Post post = postService.checkPostById(postId);

        UUID userId = UUID.fromString(authService.getCurrentUserId());

        User user = userService.findUser(userId);

        if(!userId.equals(post.getOwner().getId()) && !authService.getCurrentUserRole().equals("ADMIN")){
            throw new AppException(ErrorCode.POST_EDIT_FORBIDDEN);
        }

        return postService.updatePost(request, post);
    }

    //Xoa 1 bai viet
    public void deletePostById(UUID postId){
        Post post = postService.checkPostById(postId);

        UUID userId = UUID.fromString(authService.getCurrentUserId());

        User user = userService.findUser(userId);

        if(!userId.equals(post.getOwner().getId()) && !authService.getCurrentUserRole().equals("ADMIN")){
            throw new AppException(ErrorCode.POST_DELETE_FORBIDDEN);
        }

        postService.deletePostById(postId);
    }

    //xem chi tiet 1 bai viet
    public PostResponse getPostById(UUID postId){
        UUID viewId = UUID.fromString(authService.getCurrentUserId());

        Post post = postService.checkPostById(postId);

        UUID ownerId = post.getOwner().getId();

        User viewer = userService.findUser(viewId);

        if(!viewId.equals(ownerId) && !authService.getCurrentUserRole().equals("ADMIN") && !friendService.isFriend(ownerId,viewId)){
            throw new AppException(ErrorCode.FRIEND_NOT_FOUND);
        }

        return postService.getPostById(post);
    }

    //Lay tat ca bai viet
    public PagedResponse<PostResponse> searchAllPost(Pageable pageable){
        return postService.searchAllPost(pageable);
    }

    //Lay bai viet trang home (ban than + ban be), sap xep theo thoi gian moi nhat
    public PagedResponse<PostResponse> getNewsFeed(@PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable) {
        UUID currentUserId = UUID.fromString(authService.getCurrentUserId());

        // Lay danh sach ban be
        List<UserResponseBase> friends = friendService.getAllUserFriend(currentUserId);

        // Tao danh sach userIds gom ban than + ban be
        List<UUID> userIds = new java.util.ArrayList<>();
        userIds.add(currentUserId);
        if (friends != null && !friends.isEmpty()) {
            friends.stream().map(UserResponseBase::getUserId).forEach(userIds::add);
        }

        return postService.getNewsFeed(userIds, pageable);
    }

}
