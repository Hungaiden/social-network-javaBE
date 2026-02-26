package com.example.FakeBook.Service;

import java.util.UUID;

public interface PostRateLimitService {
    void validateCanCreatePost(UUID userId);
}
