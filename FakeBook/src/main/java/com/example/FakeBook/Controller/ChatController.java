package com.example.FakeBook.Controller;

import com.example.FakeBook.DTO.Request.ChatPrivateMessageRequestDTO;
import com.example.FakeBook.facade.ChatFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatFacadeService chatFacadeService;

    @MessageMapping("/send-message")
    public void sendPrivateMessage(ChatPrivateMessageRequestDTO request) {
        chatFacadeService.sendMessage(request);
    }
}
