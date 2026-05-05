package com.example.FakeBook.Controller;

import com.example.FakeBook.DTO.Request.UserCreationRequest;
import com.example.FakeBook.DTO.Request.UserUpdateRequest;
import com.example.FakeBook.DTO.Response.*;
import com.example.FakeBook.facade.UserFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "user")
@RequiredArgsConstructor
public class UserController {
    private final UserFacadeService userFacadeService;

    @Operation(
            summary = "Tao user moi",
            description = "Day la api tao user"
    )
    @PostMapping
    public ApiResponse<UserDetailResponse> createUser(@Valid @RequestBody UserCreationRequest request) {
        return ApiResponse.<UserDetailResponse>builder()
                .code(1000)
                .Result(userFacadeService.createUser(request))
                .build();
    }

    @Operation(
            summary = "Cap nhat thong tin",
            description = "Day la api cap nhat 1 user"
    )
    @PutMapping()
    public ApiResponse<UserDetailResponse> updateUser(@Valid @RequestBody UserUpdateRequest request){
        UserDetailResponse userResponse = userFacadeService.updateUser(request);
        return ApiResponse.<UserDetailResponse>builder()
                .code(1000)
                .message("Cap nhat thanh cong thong tin nguoi dung")
                .Result(userResponse)
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<UserResponseBase> searchUser(@RequestParam(name = "email") String email) {
        return ApiResponse.<UserResponseBase>builder()
                .code(1000)
                .message("Tim kiem thanh cong!")
                .Result(userFacadeService.searchUser(email))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ApiResponse<List<UserDetailResponse>> getAllUsers() {
        return ApiResponse.<List<UserDetailResponse>>builder()
                .code(1000)
                .Result(userFacadeService.getAllUser())
                .build();
    }

    @GetMapping("/{userId}/post")
    public ApiResponse<PagedResponse<PostResponse>> getPostByUserId(Pageable pageable, @PathVariable UUID userId){

        PagedResponse<PostResponse> pagedResponse = userFacadeService.getPostByUserId(pageable,userId);

        return ApiResponse.<PagedResponse<PostResponse>>builder()
                .code(1000)
                .Result(pagedResponse)
                .build();
    }

}
