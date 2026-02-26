package com.example.FakeBook.Mapper;

import com.example.FakeBook.DTO.Request.PostCreationRequest;
import com.example.FakeBook.DTO.Request.PostUpdateRequest;
import com.example.FakeBook.DTO.Response.PostResponse;
import com.example.FakeBook.Entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {
    Post toEntity(PostCreationRequest request);

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.username", target = "ownerUsername")
    @Mapping(source = "owner.displayName", target = "ownerDisplayName")
    @Mapping(source = "owner.avatar", target = "ownerAvatar")
    PostResponse toResponse(Post post);

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.username", target = "ownerUsername")
    @Mapping(source = "owner.displayName", target = "ownerDisplayName")
    @Mapping(source = "owner.avatar", target = "ownerAvatar")
    List<PostResponse> toResponseList(List<Post> posts);

    void updatePostFromDto(PostUpdateRequest request, @MappingTarget Post post);
}
