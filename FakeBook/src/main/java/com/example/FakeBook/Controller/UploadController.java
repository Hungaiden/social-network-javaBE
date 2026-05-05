package com.example.FakeBook.Controller;

import com.example.FakeBook.DTO.Response.ApiResponse;
import com.example.FakeBook.facade.UploadFacadeService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/upload")
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UploadController {
    final UploadFacadeService uploadFacadeService;

    @PostMapping("/image")
    public ApiResponse<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.builder()
                .code(1000)
                .Result(uploadFacadeService.uploadImageToCloudinary(file))
                .message("Upload image successfully")
                .build();
    }
}
