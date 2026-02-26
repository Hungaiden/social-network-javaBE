package com.example.FakeBook.Mapper;

import com.example.FakeBook.DTO.Response.FriendRequestResponse;
import com.example.FakeBook.Entity.FriendRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FriendRequestMapper {
    FriendRequestResponse toResponse(FriendRequest friendRequest);
}
