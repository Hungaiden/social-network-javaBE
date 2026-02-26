package com.example.FakeBook.Entity;

import com.example.FakeBook.Enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
@Entity
@Table(name = "Comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "content_type", nullable = false)
    private String contentType; // Ví dụ: "TEXT", "MEDIA"

    @Column(columnDefinition = "TEXT")
    private String content;


    private String mediaPath;

    private String mediaContent_id;

    // user_id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // post_id
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}