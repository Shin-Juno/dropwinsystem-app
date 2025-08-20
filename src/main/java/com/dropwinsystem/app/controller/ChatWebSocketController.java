package com.dropwinsystem.app.controller;

import com.dropwinsystem.app.domain.ChatMessage;
import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.repository.ChatRoomRepository;
import com.dropwinsystem.app.repository.ChatMessageRepository;
import com.dropwinsystem.app.service.ChatService; 
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketController extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("ChatWebSocketController: 새로운 웹소켓 연결: " + session.getId());

        Member member = (Member) session.getAttributes().get("loginUser");

        if (member != null && member.getId() != null) {
            String userId = member.getId();
            session.getAttributes().put("userId", userId);
            userSessions.put(userId, session);
            System.out.println("ChatWebSocketController: 웹소켓 세션에 사용자 ID 저장됨: " + userId);
            System.out.println("현재 연결된 사용자 수: " + userSessions.size());
        } else {
            String anonymousId = "anonymous-" + session.getId();
            session.getAttributes().put("userId", anonymousId);
            userSessions.put(anonymousId, session);
            System.out.println("ChatWebSocketController: 인증되지 않은 연결. 임시 사용자 ID 할당됨: " + anonymousId);
        }
    }

    @Override
    @Transactional
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("ChatWebSocketController: 메시지 수신 - 세션 ID: " + session.getId() + ", 메시지: " + payload);

        try {
            Map<String, String> receivedData = objectMapper.readValue(payload, Map.class);
            String messageType = receivedData.get("type");
            String receiverId = receivedData.get("receiverId");
            String chatMessageContent = receivedData.get("message"); 

            String senderId = (String) session.getAttributes().get("userId");

            if ("chat".equals(messageType) && receiverId != null && !receiverId.isEmpty() && senderId != null) {
            	
                String chatRoomIdentifier = chatService.getOrCreateChatRoom(senderId, receiverId);

                ChatMessage savedMessage = chatService.saveChatMessage(
                    chatRoomIdentifier,
                    senderId,
                    receiverId,
                    chatMessageContent
                );
                System.out.println("ChatWebSocketController: 메시지 저장 완료 - " + chatRoomIdentifier + ", ID: " + savedMessage.getId());

                Map<String, String> commonPayload = new HashMap<>();
                commonPayload.put("type", "chat");
                commonPayload.put("chatRoomId", chatRoomIdentifier);
                commonPayload.put("senderId", senderId);
                commonPayload.put("receiverId", receiverId);
                commonPayload.put("message", chatMessageContent);
                commonPayload.put("timestamp", savedMessage.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                commonPayload.put("isRead", savedMessage.getIsRead().toString());

                String jsonPayload = objectMapper.writeValueAsString(commonPayload);

                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(jsonPayload));
                    System.out.println("ChatWebSocketController: 발신자(" + senderId + ")에게 메시지 전송 완료");
                }

                WebSocketSession receiverSession = userSessions.get(receiverId);
                if (receiverSession != null && receiverSession.isOpen()) {
                    receiverSession.sendMessage(new TextMessage(jsonPayload));
                    System.out.println("ChatWebSocketController: 수신자(" + receiverId + ")에게 메시지 전송 완료");
                } else {
                    System.out.println("ChatWebSocketController: 수신자(" + receiverId + ") 세션이 없거나 닫혀 있습니다. 메시지 DB에만 저장.");
                }
            } else {
                System.out.println("ChatWebSocketController: 알 수 없는 메시지 타입, 수신자 ID 또는 발신자 ID 누락.");
            }
        } catch (IOException e) {
            System.err.println("ChatWebSocketController: JSON 파싱 오류 또는 메시지 처리 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            userSessions.remove(userId);
            System.out.println("ChatWebSocketController: 웹소켓 연결 종료 및 사용자(" + userId + ") 세션 제거: " + session.getId() + ", 상태: " + status.getCode());
        } else {
            System.out.println("ChatWebSocketController: 웹소켓 연결 종료: " + session.getId() + ", 상태: " + status.getCode());
        }
        System.out.println("현재 연결된 사용자 수: " + userSessions.size());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("ChatWebSocketController: 웹소켓 전송 에러 - 세션 ID: " + session.getId() + ", 에러: " + exception.getMessage());
        exception.printStackTrace();
    }
}