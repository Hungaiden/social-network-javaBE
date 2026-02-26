package com.example.FakeBook.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespondFriendRequest extends FriendRequestNotificationBase{
    private UUID requestId;
    private UUID receiverId;
    private Action action;

    public static enum Action {
        ACCEPT, REJECT
    }
}
