package com.example.FakeBook.facade;

import com.example.FakeBook.DTO.Request.ChatPrivateMessageRequestDTO;
import com.example.FakeBook.DTO.Response.MessageResponseDTO;
import com.example.FakeBook.Entity.Conversation;
import com.example.FakeBook.Entity.ConversationMember;
import com.example.FakeBook.Entity.User;
import com.example.FakeBook.Enums.ErrorCode;
import com.example.FakeBook.Exception.AppException;
import com.example.FakeBook.Service.ChatService;
import com.example.FakeBook.Service.ConversationMemberService;
import com.example.FakeBook.Service.ConversationService;
import com.example.FakeBook.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatFacadeService {

    private final ChatService chatService;
    private final ConversationMemberService conversationMemberService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ConversationService conversationService;
    private final UserService userService;

    public void sendMessage (ChatPrivateMessageRequestDTO request) {

        Conversation conversation = conversationService.findConversationById(request.getConversationId());
        User sender = userService.findUser(request.getSenderId());
        MessageResponseDTO savedMsg = chatService.saveMessage(request, conversation, sender);
        // send message by socket
        UUID senderId = request.getSenderId();
        List<ConversationMember> members = conversationMemberService.findAllByConversationId(request.getConversationId());
        List<UUID> userIds = members.stream()
                .map(member -> member.getUser().getId())
                .toList();
        List<UUID> receiverIds = userIds.stream()
                .filter(id -> !id.equals(senderId))
                .toList();
        for(UUID receiverId: receiverIds) {
            simpMessagingTemplate.convertAndSendToUser(
                    receiverId.toString(),
                    "/queue/messages",
                    savedMsg
            );
        }
    }
}
