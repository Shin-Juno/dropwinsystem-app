package com.dropwinsystem.app.controller;

import com.dropwinsystem.app.domain.ChatMessage;
import com.dropwinsystem.app.domain.ChatRoom;
import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.service.ChatService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class MessageController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @GetMapping("/mypage/inbox")
    public String getInbox(Model model, HttpSession session) {
        String currentUserId;
        Member loggedInMember = (Member) session.getAttribute("loginUser");

        if (loggedInMember != null && loggedInMember.getId() != null) {
            currentUserId = loggedInMember.getId();
            System.out.println("DEBUG: Inbox - HttpSession에서 가져온 로그인 사용자 ID: " + currentUserId);
        } else {
            System.err.println("ERROR: Inbox - HttpSession에 유효한 로그인된 사용자 ID가 없습니다. 로그인 페이지로 리다이렉트합니다.");
            return "redirect:/loginForm";
        }

        List<ChatRoom> chatRooms = chatService.getChatRoomsForUser(currentUserId);
        model.addAttribute("chatRooms", chatRooms);
        model.addAttribute("loggedInUserId", currentUserId);
        System.out.println("DEBUG: Inbox - 사용자 " + currentUserId + "의 채팅방 " + chatRooms.size() + "개 로드.");
        return "views/inbox";
    }

    @GetMapping("/chat")
    public String getChatRoom(Model model,
                              @RequestParam("partnerId") String partnerId,
                              HttpSession session) {

        System.out.println("DEBUG: ChatController - /chat 요청 수신.");
        System.out.println("DEBUG: ChatController - 요청 파라미터 partnerId: " + partnerId); // ★★★ partnerId 로그 강화 ★★★

        String currentUserId;
        Member loggedInMember = (Member) session.getAttribute("loginUser");

        if (loggedInMember != null && loggedInMember.getId() != null) {
            currentUserId = loggedInMember.getId();
            System.out.println("DEBUG: ChatController - HttpSession에서 가져온 로그인 사용자 ID: " + currentUserId);
        } else {
            System.err.println("ERROR: ChatController - HttpSession에 유효한 로그인된 사용자 ID가 없습니다. 로그인 페이지로 리다이렉트합니다.");
            return "redirect:/loginForm";
        }

        if (currentUserId.equals(partnerId)) {
            System.out.println("DEBUG: ChatController - 자기 자신과의 채팅 시도. 인박스 페이지로 리다이렉트합니다.");
            return "redirect:/mypage/inbox";
        }

        System.out.println("DEBUG: ChatController - 최종 currentUserId: " + currentUserId + ", partnerId: " + partnerId);
        String chatRoomId = chatService.getOrCreateChatRoom(currentUserId, partnerId);
        System.out.println("DEBUG: ChatController - getOrCreateChatRoom 호출 결과 chatRoomId: " + chatRoomId);


        List<ChatMessage> chatHistory = chatService.getChatHistory(chatRoomId);
        model.addAttribute("chatHistory", chatHistory);

        model.addAttribute("partnerId", partnerId);
        model.addAttribute("loggedInUserId", currentUserId);
        model.addAttribute("chatRoomId", chatRoomId);
        System.out.println("DEBUG: ChatController - 채팅방 페이지 로드 완료. 사용자: " + currentUserId + ", 상대방: " + partnerId + ", 채팅방 ID: " + chatRoomId);
        System.out.println("DEBUG: ChatController - 채팅 기록 " + chatHistory.size() + "개 로드.");
        return "views/chat";
    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        System.out.println("DEBUG: STOMP - 메시지 수신: " + chatMessage.getMessageContent() + " from " + chatMessage.getSenderId());
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessage.setIsRead(false);

        ChatMessage savedMessage = chatService.saveChatMessage(
            chatMessage.getChatRoomId(),
            chatMessage.getSenderId(),
            chatMessage.getReceiverId(),
            chatMessage.getMessageContent()
        );
        System.out.println("DEBUG: STOMP - 메시지 저장됨. ID: " + savedMessage.getId());

        messagingTemplate.convertAndSend("/topic/chat.room." + savedMessage.getChatRoomId(), savedMessage);
        System.out.println("DEBUG: STOMP - 메시지 브로드캐스트됨: /topic/chat.room." + savedMessage.getChatRoomId());
    }

    @GetMapping("/api/chat/messages")
    @ResponseBody
    public ResponseEntity<List<ChatMessage>> getChatMessages(@RequestParam("chatRoomId") String chatRoomId) {
        System.out.println("DEBUG: API - /api/chat/messages 요청. chatRoomId: " + chatRoomId);
        List<ChatMessage> messages = chatService.getChatHistory(chatRoomId);
        System.out.println("DEBUG: API - chatRoomId " + chatRoomId + "에 대한 메시지 " + messages.size() + "개 로드.");
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/api/chat/markAsRead")
    @ResponseBody
    public ResponseEntity<String> markMessagesAsRead(@RequestBody Map<String, String> payload) {
        String chatRoomId = payload.get("chatRoomId");
        String userId = payload.get("userId");

        System.out.println("DEBUG: API - /api/chat/markAsRead 요청. chatRoomId: " + chatRoomId + ", userId: " + userId);

        if (chatRoomId == null || userId == null) {
            System.err.println("ERROR: API - markAsRead 필수 파라미터 누락.");
            return ResponseEntity.badRequest().body("필수 파라미터 chatRoomId 또는 userId가 누락되었습니다.");
        }

        chatService.markAllMessagesInChatRoomAsRead(chatRoomId, userId);
        System.out.println("DEBUG: API - 채팅방 " + chatRoomId + "에서 사용자 " + userId + "가 받은 메시지를 읽음으로 표시했습니다.");
        return ResponseEntity.ok("메시지 읽음 처리 완료.");
    }
}
