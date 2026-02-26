package com.example.FakeBook.DTO.Request;

import com.example.FakeBook.Entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequestDTO {
    @NotNull
    private User userA;

    @NotNull
    private User userB;

}
