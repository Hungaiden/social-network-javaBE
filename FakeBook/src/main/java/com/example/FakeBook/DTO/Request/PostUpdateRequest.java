package com.example.FakeBook.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostUpdateRequest {

    @Size(max = 500, message = "DISPLAYTITLE_INVALID")
    String title;

    @Size(max = 5000, message = "DISPLAYCONTENT_INVALID")
    String content;
}
