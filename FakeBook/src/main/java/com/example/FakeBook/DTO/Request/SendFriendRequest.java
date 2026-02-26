package com.example.FakeBook.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendFriendRequest extends FriendRequestNotificationBase{
    private UUID receiverId;
}
