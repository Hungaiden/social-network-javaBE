package com.example.FakeBook.Service.impl;

import com.example.FakeBook.Service.PostRateLimitService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// import com.example.FakeBook.Enums.ErrorCode;
// import com.example.FakeBook.Exception.AppException;
// import lombok.RequiredArgsConstructor;
// import org.springframework.data.redis.core.StringRedisTemplate;
// import java.time.Duration;
import java.util.UUID;

@Service
// @RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRateLimitServiceImpl implements PostRateLimitService {

    // final StringRedisTemplate stringRedisTemplate;

    @Override
    public void validateCanCreatePost(UUID userId) {
        // String key = "post_count:" + userId;
        //
        // Long count = stringRedisTemplate.opsForValue().increment(key);
        //
        // if (count == 1) {
        //     stringRedisTemplate.expire(key, Duration.ofSeconds(60));
        // }
        // if (count > 3) {
        //     throw new AppException(ErrorCode.POST_TOO_FAST);
        // }
    }
}
