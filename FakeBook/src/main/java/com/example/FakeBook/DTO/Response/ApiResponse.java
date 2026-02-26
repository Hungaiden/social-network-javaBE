package com.example.FakeBook.DTO.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ApiResponse<T> {
    private int code;
    private String message;
    private T Result;

    public static ApiResponse<?> success(Object response) {
        return ApiResponse.builder()
                .code(200)
                .message("Success")
                .Result(response)
                .build();
    }
}
