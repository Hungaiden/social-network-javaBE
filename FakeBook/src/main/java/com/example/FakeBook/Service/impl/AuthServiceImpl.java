package com.example.FakeBook.Service.impl;

import com.example.FakeBook.DTO.Response.UserDetailResponse;
import com.example.FakeBook.Entity.InvalidatedToken;
import com.example.FakeBook.Entity.User;
import com.example.FakeBook.Enums.ErrorCode;
import com.example.FakeBook.Exception.AppException;
import com.example.FakeBook.Mapper.UserMapper;
import com.example.FakeBook.Repository.InvalidatedTokenRepository;
import com.example.FakeBook.Repository.UserRepository;
import com.example.FakeBook.Service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    final UserRepository userRepository;

    final UserMapper userMapper;

    final InvalidatedTokenRepository invalidatedTokenRepository;

    final PasswordEncoder passwordEncoder;

    @Override
    public UserDetailResponse myInfo(String id){
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserDetailResponse(user);
    }

    @Override
    public User login(String userName, String password) {
        User user = userRepository.findByUsername(userName).orElseThrow(() ->  new AppException(ErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(password, user.getPassword())) throw new AppException(ErrorCode.WRONG_PASSWORD);
        return user;
    }

    @Override
    public void logout(String id, Date expiryTime) {
        // Luu vao database
        invalidatedTokenRepository.save(InvalidatedToken.builder().id(id).expiryTime(expiryTime).build());
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            String userId = jwt.getSubject();
            return userRepository.findById(UUID.fromString(userId))
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        }

        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    @Override
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            String userId = jwt.getSubject();
            return userId;
        }

        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    @Override
    public String getCurrentUserDisplayName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaim("displayName").toString();
        }

        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    @Override
    public String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaim("scope").toString();
        }

        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

}
