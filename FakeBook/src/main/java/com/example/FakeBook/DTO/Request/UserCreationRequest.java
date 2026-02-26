package com.example.FakeBook.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationRequest {
    @NotBlank(message = "DISPLAYNAME_NOT_NULL")
    @Size(min = 10, message = "DISPLAYNAME_INVALID")
    private String displayName;

    @NotBlank(message = "USERNAME_NOT_NULL")
    @Size(min = 10, message = "USERNAME_INVALID")
    private String username;

    @NotBlank(message = "PASSWORD_NOT_NULL")
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;

    @Email(message = "EMAIL_INVALID")
    @NotBlank(message = "EMAIL_NOT_NULL")
    private String email;

}
