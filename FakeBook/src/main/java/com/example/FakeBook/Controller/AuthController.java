package com.example.FakeBook.Controller;
import com.example.FakeBook.DTO.Request.AuthenticationRequest;
import com.example.FakeBook.DTO.Response.ApiResponse;
import com.example.FakeBook.DTO.Response.AuthenticationResponse;
import com.example.FakeBook.DTO.Response.UserDetailResponse;
import com.example.FakeBook.facade.AuthFacadeService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "auth")

public class AuthController {
    @Autowired
    private AuthFacadeService authFacadeService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .Result(authFacadeService.login(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() throws ParseException, JOSEException {
        authFacadeService.logout();
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Logout thanh cong!")
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserDetailResponse> myInfo() throws ParseException, JOSEException {
        return ApiResponse.<UserDetailResponse>builder()
                .code(1000)
                .message("Lay thanh cong thong tin nguoi dung")
                .Result(authFacadeService.myInfo())
                .build();
    }
}
