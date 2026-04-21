package com.example.FakeBook.Controller;
import com.example.FakeBook.DTO.Request.AuthenticationRequest;
import com.example.FakeBook.DTO.Request.UserCreationRequest;
import com.example.FakeBook.DTO.Response.ApiResponse;
import com.example.FakeBook.DTO.Response.AuthenticationResponse;
import com.example.FakeBook.DTO.Response.UserDetailResponse;
import com.example.FakeBook.facade.AuthFacadeService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "auth")
public class AuthController {
    @Autowired
    private AuthFacadeService authFacadeService;

    @Operation(
            summary = "Dang ky tai khoan",
            description = "Dang ky tai khoan moi va tu dong dang nhap"
    )
    @PostMapping("/register")
    public ApiResponse<AuthenticationResponse> register(@Valid @RequestBody UserCreationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .message("Dang ky thanh cong!")
                .Result(authFacadeService.register(request))
                .build();
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .Result(authFacadeService.login(request))
                .build();
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/logout")
    public ApiResponse<Void> logout() throws ParseException, JOSEException {
        authFacadeService.logout();
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Logout thanh cong!")
                .build();
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/myInfo")
    public ApiResponse<UserDetailResponse> myInfo() throws ParseException, JOSEException {
        return ApiResponse.<UserDetailResponse>builder()
                .code(1000)
                .message("Lay thanh cong thong tin nguoi dung")
                .Result(authFacadeService.myInfo())
                .build();
    }
}
