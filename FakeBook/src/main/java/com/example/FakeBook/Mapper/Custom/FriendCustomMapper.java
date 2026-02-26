package com.example.FakeBook.Mapper.Custom;

import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.DTO.Response.UserResponseBase;
import com.example.FakeBook.Entity.Friend;
import com.example.FakeBook.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FriendCustomMapper {
    public PagedResponse<UserResponseBase> toPageResponse(Page<Friend> friendPage, UUID userId) {
        List<Friend> friendList = friendPage.getContent();

        if (friendList == null || friendList.isEmpty()) return null;

        List<UserResponseBase> friendResponseList = new ArrayList<>();
        for (Friend x : friendList) {
            User friend = (x.getUserA().getId().equals(userId)) ? x.getUserB() : x.getUserA();
            friendResponseList.add(
                    UserResponseBase.builder()
                            .userId(friend.getId())
                            .displayName(friend.getDisplayName())
                            .avatar(friend.getAvatar())
                            .build()
            );
        }
        return PagedResponse.<UserResponseBase>builder()
                .content(friendResponseList)
                .page(friendPage.getNumber())
                .size(friendPage.getSize())
                .totalElements(friendPage.getTotalElements())
                .totalPages(friendPage.getTotalPages())
                .last(friendPage.isLast())
                .build();
    }
    public List<UserResponseBase> toListResponse(List<Friend> friendList, UUID userId) {
        if (friendList == null || friendList.isEmpty()) return null;

        List<UserResponseBase> friendResponseList = new ArrayList<>();
        for (Friend x : friendList) {
            User friend = (x.getUserA().getId().equals(userId)) ? x.getUserB() : x.getUserA();
            friendResponseList.add(
                    UserResponseBase.builder()
                            .userId(friend.getId())
                            .displayName(friend.getDisplayName())
                            .avatar(friend.getAvatar())
                            .build()
            );
        }
        return friendResponseList;
    }
}
