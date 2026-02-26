package com.example.FakeBook.Service;

import com.example.FakeBook.DTO.Request.UserCreationRequest;
import com.example.FakeBook.DTO.Request.UserUpdateRequest;
import com.example.FakeBook.DTO.Response.UserDetailResponse;
import com.example.FakeBook.DTO.Response.UserResponseBase;
import com.example.FakeBook.Entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    UserDetailResponse createUser(UserCreationRequest request);

    UserDetailResponse updateUser(UserUpdateRequest request, UUID userId);

    User findUser(UUID Id);
    List<UserDetailResponse> getAllUser();

    List<User> findUsersByIds(Set<UUID> memberIds);

    UserResponseBase searchUser(String email);

    Optional<User> findById(UUID userId);
}
