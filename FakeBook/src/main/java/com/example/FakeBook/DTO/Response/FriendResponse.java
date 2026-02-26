package com.example.FakeBook.DTO.Response;

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
public class FriendResponse {
    @NotNull
    private UUID id;

    @NotNull
    private UUID userA_Id;

    @NotNull
    private UUID userB_Id;

}
