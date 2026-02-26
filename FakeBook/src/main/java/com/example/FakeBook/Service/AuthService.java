package com.example.FakeBook.Service;

import com.example.FakeBook.DTO.Response.UserDetailResponse;
import com.example.FakeBook.Entity.User;

import java.util.Date;

public interface AuthService {
    UserDetailResponse myInfo(String id);
    User login(String userName, String password);
    void logout(String id, Date expiryTime);
    User getCurrentUser();
    String getCurrentUserId();
    String getCurrentUserDisplayName();
    String getCurrentUserRole();
}
