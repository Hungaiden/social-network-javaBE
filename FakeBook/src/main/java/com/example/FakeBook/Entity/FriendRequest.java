package com.example.FakeBook.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "FriendRequest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // sender_id
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // receiver_id
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    private String message;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at") // Lưu ý tên cột trong SQL là update_at
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }
}