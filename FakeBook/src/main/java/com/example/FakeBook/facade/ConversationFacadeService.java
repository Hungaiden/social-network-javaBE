package com.example.FakeBook.facade;

import com.example.FakeBook.DTO.Request.AddMembersRequest;
import com.example.FakeBook.DTO.Request.CreateGroupConversationRequest;
import com.example.FakeBook.DTO.Request.CreatePrivateConversationRequest;
import com.example.FakeBook.DTO.Response.*;
import com.example.FakeBook.Entity.Conversation;
import com.example.FakeBook.Entity.ConversationMember;
import com.example.FakeBook.Entity.User;
import com.example.FakeBook.Enums.ErrorCode;
import com.example.FakeBook.Exception.AppException;
import com.example.FakeBook.Mapper.ConversationMapper;
import com.example.FakeBook.Mapper.Custom.ConversationCustomMapper;
import com.example.FakeBook.Service.AuthService;
import com.example.FakeBook.Service.ConversationMemberService;
import com.example.FakeBook.Service.ConversationService;
import com.example.FakeBook.Service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
@Slf4j
public class ConversationFacadeService {

    private final AuthService authService;

    private final UserService userService;

    private final ConversationService conversationService;

    private final ConversationMemberService conversationMemberService;

    private final ConversationMapper conversationMapper;

    private final ConversationCustomMapper conversationCustomMapper;
    @Transactional
    public CreatePrivateConversationResponse createPrivateConversationResponse (CreatePrivateConversationRequest request) {

        UUID currentUserId = UUID.fromString(authService.getCurrentUserId());
        UUID targetUserId = request.getTargetUserId();

        if (currentUserId.equals(targetUserId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        User userA = userService.findUser(currentUserId);
        User userB = userService.findUser(targetUserId);

        // check existing (both directions)
        if (conversationService.findPrivateBetween(currentUserId, targetUserId).isPresent()
                || conversationService.findPrivateBetween(targetUserId, currentUserId).isPresent()) {
            throw new AppException(ErrorCode.CONVERSATION_EXIST);
        }

        // create conversation
        Conversation conversation = conversationService.createPrivateConversation();

        // add members
        conversationMemberService.addMember(conversation, userA);
        conversationMemberService.addMember(conversation, userB);

        return conversationMapper.toPrivateConversationResponse(conversation);
    }

    @Transactional
    public CreateGroupConversationResponse createGroupConversation(
            @Valid CreateGroupConversationRequest request) {

        UUID currentUserId = UUID.fromString(authService.getCurrentUserId());
        Set<UUID> memberIds = new HashSet<>(request.getMemberIds());

        // Validate
        memberIds.add(currentUserId);
        conversationService.validateInputConversation(memberIds);

        List<User> users = userService.findUsersByIds(memberIds);

        //  Create group conversation
        Conversation conversation = conversationService.createGroupConversation(request.getName());

        //  Add members (current user = admin)
        for (User user : users) {
            boolean isAdmin = user.getId().equals(currentUserId);
            conversationMemberService.addMember(conversation, user, isAdmin);
        }

        // Get all member of converation
        UUID conversationId = conversation.getId();
        List<ConversationMember> membersInDb = conversationMemberService.findAllByConversationId(conversationId);

        //  Map to response
        return conversationCustomMapper.toGroupConversationResponse(conversation, users, membersInDb);
    }

    public AddMembersResponse addMembers (UUID conversationId, AddMembersRequest request) {

        UUID currentUserId = UUID.fromString(authService.getCurrentUserId());

        // validate
        Conversation conversation = conversationService.findConversationById(conversationId);
        if (conversation.getType().equals("PRIVATE")) {
            throw new AppException(ErrorCode.INVALID_CONVERSATION_TYPE);
        }
        boolean isAdmin = conversationService.isAdmin(conversationId, currentUserId);
        if (!isAdmin) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }

        //  Lấy danh sách users cần thêm
        Set<UUID> ids = new HashSet<>(request.getUserIds());
        List<User> users = userService.findUsersByIds(ids);

        if (users.size() != ids.size()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        // Thêm lần lượt
        List<UUID> addedUserIds = new ArrayList<>();

        for (User user : users) {
            // skip nếu user đã trong group → không throw lỗi
            boolean exists = conversationMemberService
                    .exists(conversation, user);

            if (!exists) {
                conversationMemberService.addMember(conversation, user, false);
                addedUserIds.add(user.getId());
            }
        }

        return new AddMembersResponse(conversationId, addedUserIds);
    }

    public PagedResponse<ConversationResponse> getAllConversations (Pageable pageable) {
        UUID currentUserId = UUID.fromString(authService.getCurrentUserId());

        PagedResponse<ConversationResponse> conversations = conversationService.findPrivateConversationByUserId(currentUserId, pageable);

        return conversations;
    }
}
