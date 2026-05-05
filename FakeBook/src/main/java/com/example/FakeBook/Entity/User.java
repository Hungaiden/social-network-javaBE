package com.example.FakeBook.Entity;

import com.example.FakeBook.Enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private String avatar;

    private String coverPhoto; // Ảnh bìa

    private String bio;        // Tiểu sử ngắn

    private String address;    // Địa chỉ

    private LocalDate dob;     // Ngày sinh

    private String occupation; // Nghề nghiệp

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // --- Mối quan hệ ---

    // Friend (Người dùng A)
    @OneToMany(mappedBy = "userA")
    private Set<Friend> friendsAsUserA;

    // Friend (Người dùng B)
    @OneToMany(mappedBy = "userB")
    private Set<Friend> friendsAsUserB;

    // FriendRequest (Người gửi)
    @OneToMany(mappedBy = "sender")
    private Set<FriendRequest> sentFriendRequests;

    // FriendRequest (Người nhận)
    @OneToMany(mappedBy = "receiver")
    private Set<FriendRequest> receivedFriendRequests;

    // Message (Người gửi)
    @OneToMany(mappedBy = "sender")
    private Set<Message> sentMessages;

    // ConversationMembers (Thành viên)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ConversationMember> memberships;

    // MediaMessage (Người gửi)
    @OneToMany(mappedBy = "sender")
    private Set<MediaMessage> sentMediaMessages;

    // Post (Chủ sở hữu)
    @OneToMany(mappedBy = "owner")
    private Set<Post> posts;

    // Comment (Người dùng)
    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;

    // PostReaction (Người dùng)
    @OneToMany(mappedBy = "user")
    private Set<PostReaction> postReactions;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.role = Role.USER.toString();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
