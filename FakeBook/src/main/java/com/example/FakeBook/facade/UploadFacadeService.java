package com.example.FakeBook.facade;

import com.example.FakeBook.Service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UploadFacadeService {
    private final CloudinaryService cloudinaryService;

    public String uploadImageToCloudinary(MultipartFile file) throws IOException {
        return cloudinaryService.uploadImage(file);
    }
}
