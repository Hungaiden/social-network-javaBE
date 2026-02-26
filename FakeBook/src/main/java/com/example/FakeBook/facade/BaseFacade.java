package com.example.FakeBook.facade;

import com.example.FakeBook.DTO.Response.ApiResponse;
import org.springframework.http.ResponseEntity;

public abstract class BaseFacade {
    protected ResponseEntity<?> success(Object response) {
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
