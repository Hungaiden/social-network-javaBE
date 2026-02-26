package com.example.FakeBook.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
@Entity
@Table(name = "Conversation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String avatar;
    private String name;

    @Column(nullable = false)
    private String type; // PRIVATE hoặc GROUP

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    // --- Mối quan hệ ---

    // Message
    @OneToMany(mappedBy = "conversation")
    private Set<Message> messages;

    // ConversationMembers
    @OneToMany(mappedBy = "conversation", orphanRemoval = true)
    @Builder.Default
    private Set<ConversationMember> members = new HashSet<>();

    // MediaMessage
    @OneToMany(mappedBy = "conversation")
    private Set<MediaMessage> mediaMessages;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}