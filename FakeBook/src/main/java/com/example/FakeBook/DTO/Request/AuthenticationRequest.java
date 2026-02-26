package com.example.FakeBook.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Schema(example = "longtran0204")
    @NotBlank(message = "USERNAME_NOT_NULL")
    private String username;

    @Schema(example = "12345678")
    @NotBlank(message = "PASSWORD_NOT_NULL")
    private String password;
}
