package com.example.FakeBook.Service.impl;

import com.example.FakeBook.DTO.Request.UserCreationRequest;
import com.example.FakeBook.DTO.Request.UserUpdateRequest;
import com.example.FakeBook.DTO.Response.UserDetailResponse;
import com.example.FakeBook.DTO.Response.UserResponseBase;
import com.example.FakeBook.Entity.User;
import com.example.FakeBook.Enums.ErrorCode;
import com.example.FakeBook.Exception.AppException;
import com.example.FakeBook.Mapper.UserMapper;
import com.example.FakeBook.Repository.UserRepository;
import com.example.FakeBook.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Tao nguoi dung moi

    @Override
    public UserDetailResponse createUser(UserCreationRequest request) {
        //Kiem tra ton tai
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USERNAME_EXIST);
        if (userRepository.existsByEmail(request.getEmail())) throw new AppException(ErrorCode.EMAIL_EXIST);

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toUserDetailResponse(userRepository.save(user));
    }

    //Cap nhat thong tin nguoi dung
    @Override
    public UserDetailResponse updateUser(UserUpdateRequest request, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!user.getUsername().equals(request.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USERNAME_EXIST);
        }
        userMapper.update(user, request);

        // Xử lý riêng phần password vì cần encode
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new AppException(ErrorCode.PASSWORD_DUPLICATED);
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userMapper.toUserDetailResponse(userRepository.save(user));
    }

    @Override
    public User findUser(UUID Id) {
        return userRepository.findById(Id).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    //Lay all user
    @Override
    public List<UserDetailResponse> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserDetailResponse> userResponsesList = userMapper.toUserDetailResponseList(userList);
        return userResponsesList;
    }

    @Override
    public List<User> findUsersByIds(Set<UUID> memberIds) {
        List<User> users = userRepository.findAllById(memberIds);
        return users;
    }

    @Override
    public UserResponseBase searchUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserBaseResponse(user);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }
}
