package com.example.FakeBook.Mapper;

import com.example.FakeBook.DTO.Request.UserCreationRequest;
import com.example.FakeBook.DTO.Request.UserUpdateRequest;
import com.example.FakeBook.DTO.Response.UserDetailResponse;
import com.example.FakeBook.DTO.Response.UserResponseBase;
import com.example.FakeBook.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    User toEntity(UserCreationRequest request);

    @Mapping(source = "id", target = "userId")
    UserDetailResponse toUserDetailResponse(User user);

    @Mapping(source = "id", target = "userId")
    UserResponseBase toUserBaseResponse(User user);

    List<UserDetailResponse> toUserDetailResponseList(List<User> userList);

    @Mapping(target = "password", ignore = true)
    void update(@MappingTarget User user, UserUpdateRequest request);
}
