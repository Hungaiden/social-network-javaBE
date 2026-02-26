package com.example.FakeBook.Mapper;

import com.example.FakeBook.DTO.Response.FriendResponse;
import com.example.FakeBook.Entity.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface FriendMapper {
    FriendResponse toResponse(Friend friend);

    @Mapping(source = "userA.id", target = "userA_Id")
    @Mapping(source = "userB.id", target = "userB_Id")
    List<FriendResponse> toListResponse(List<Friend> friendList);
}
