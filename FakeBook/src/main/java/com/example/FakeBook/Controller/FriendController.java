package com.example.FakeBook.Controller;

import com.example.FakeBook.DTO.Response.ApiResponse;
import com.example.FakeBook.DTO.Response.PagedResponse;
import com.example.FakeBook.DTO.Response.UserResponseBase;
import com.example.FakeBook.facade.FriendFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
@Slf4j
public class FriendController {
    private final FriendFacadeService friendFacadeService;
//    @GetMapping
//    public ApiResponse isFriend(UUID userA_id, UUID userB_id) {
//        boolean ok = friendFacadeService.isFriend(userA_id, userB_id);
//        return ApiResponse.builder()
//                .code(1000)
//                .message((ok != true) ? "Hai nguoi khong phai ban be" : "Hai nguoi la ban be")
//                .build();
//    }

    @GetMapping("/myFriend")
    public ApiResponse<PagedResponse<UserResponseBase>> getAllFriendUser(Pageable pageable) {
        return  ApiResponse.<PagedResponse<UserResponseBase>>builder()
                .message("ok")
                .Result(friendFacadeService.searchAllFriendUser(pageable))
                .build();
    }

    @DeleteMapping()
    public ApiResponse deleteFriend (@RequestParam UUID friendID) {
        friendFacadeService.deleteFriend(friendID);
        return ApiResponse.builder()
                .code(1000)
                .message("Xoa ban be thanh cong!")
                .build();
    }

}
