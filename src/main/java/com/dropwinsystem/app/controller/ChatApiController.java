package com.dropwinsystem.app.controller;

import com.dropwinsystem.app.domain.ChatMessage;
import com.dropwinsystem.app.domain.ChatRoom;
import com.dropwinsystem.app.domain.ChatRoomDto;
import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.repository.ChatMessageRepository;
import com.dropwinsystem.app.service.ChatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class ChatApiController {

    private final ChatService chatService;
    private final ChatMessageRepository chatMessageRepository;

    public ChatApiController(ChatService chatService, ChatMessageRepository chatMessageRepository) {
        this.chatService = chatService;
        this.chatMessageRepository = chatMessageRepository;
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomDto>> getChatRooms(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
        }

        List<ChatRoom> chatRooms = chatService.getChatRoomsForUser(loginUser.getId());

        List<ChatRoomDto> chatRoomDtos = chatRooms.stream().map(chatRoom -> {
            String otherUserId = chatRoom.getUser1Id().equals(loginUser.getId()) ? chatRoom.getUser2Id() : chatRoom.getUser1Id();

            List<ChatMessage> lastMessageList = chatMessageRepository.findByChatRoomIdOrderByTimestampDesc(chatRoom.getId(), PageRequest.of(0, 1));
            ChatMessage lastMessage = lastMessageList.isEmpty() ? null : lastMessageList.get(0);

            String lastMessageContent = (lastMessage != null) ? lastMessage.getMessageContent() : "아직 대화 내용이 없습니다.";

            long unreadCount = chatMessageRepository.countByChatRoomIdAndReceiverIdAndIsReadFalse(chatRoom.getId(), loginUser.getId());

            return new ChatRoomDto(
                chatRoom.getId(),
                otherUserId,
                "상대방 이름",
                (int) unreadCount, 
                chatRoom.getCreatedAt(),
                lastMessageContent,
                (lastMessage != null) ? lastMessage.getTimestamp() : null
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(chatRoomDtos);
    }
}
