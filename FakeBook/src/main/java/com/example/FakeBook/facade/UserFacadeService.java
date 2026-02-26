package com.example.FakeBook.facade;

import com.example.FakeBook.DTO.Request.UserCreationRequest;
import com.example.FakeBook.DTO.Request.UserUpdateRequest;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.DTO.Response.PostResponse;
import com.example.FakeBook.DTO.Response.UserDetailResponse;
import com.example.FakeBook.DTO.Response.UserResponseBase;
import com.example.FakeBook.Entity.User;
import com.example.FakeBook.Enums.ErrorCode;
import com.example.FakeBook.Exception.AppException;
import com.example.FakeBook.Mapper.UserMapper;
import com.example.FakeBook.Service.AuthService;
import com.example.FakeBook.Service.FriendService;
import com.example.FakeBook.Service.PostService;
import com.example.FakeBook.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserFacadeService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final FriendService friendService;
    private final PostService postService;

    public UserDetailResponse createUser(UserCreationRequest request) {
        return userService.createUser(request);
    }

    public UserDetailResponse updateUser(UserUpdateRequest request) {
        String userId = authService.getCurrentUserId();
        return userService.updateUser(request, UUID.fromString(userId));
    }

    public List<UserDetailResponse> getAllUser() {
        return userService.getAllUser();
    }

    public UserResponseBase searchUser(String email) {
        return userService.searchUser(email);
    }

    //Lay bai viet cua 1 user
    public PagedResponse<PostResponse> getPostByUserId(Pageable pageable, UUID ownerId){

        User owner = userService.findUser(ownerId);

        UUID userId = UUID.fromString(authService.getCurrentUserId());

        User viewer = userService.findUser(userId);

        if (!userId.equals(owner.getId()) && !authService.getCurrentUserRole().equals("ADMIN") && !friendService.isFriend(ownerId,viewer.getId())) {
            throw new AppException(ErrorCode.FRIEND_NOT_FOUND);
        }

        return postService.getPostByUserId(pageable,ownerId);
    }

    // lấy conversation của user

}
