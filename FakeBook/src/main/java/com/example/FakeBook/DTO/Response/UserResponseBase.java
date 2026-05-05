package com.example.FakeBook.DTO.Response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponseBase {
    @NotNull
    private UUID userId;
    @NotNull
    private String displayName;

    private String avatar;
}
