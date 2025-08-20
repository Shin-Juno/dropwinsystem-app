package com.dropwinsystem.app.service;

import com.dropwinsystem.app.domain.ChatMessage;
import com.dropwinsystem.app.domain.ChatRoom;
import com.dropwinsystem.app.domain.ChatRoomDto;
import com.dropwinsystem.app.repository.ChatMessageRepository;
import com.dropwinsystem.app.repository.ChatRoomRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatService(ChatRoomRepository chatRoomRepository, ChatMessageRepository chatMessageRepository, SimpMessagingTemplate messagingTemplate) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Transactional
    public String getOrCreateChatRoom(String user1, String user2) {
        List<String> participants = Arrays.asList(user1, user2);
        participants.sort(String::compareTo);
        String chatRoomId = participants.get(0) + "_" + participants.get(1);

        Optional<ChatRoom> existingRoom = chatRoomRepository.findById(chatRoomId);

        if (existingRoom.isPresent()) {
            System.out.println("기존 채팅방 발견: " + chatRoomId);
            return existingRoom.get().getId();
        } else {
            ChatRoom newRoom = new ChatRoom(chatRoomId, participants.get(0), participants.get(1));
            chatRoomRepository.save(newRoom);
            System.out.println("새 채팅방 생성 및 저장: " + chatRoomId);
            return newRoom.getId();
        }
    }

    @Transactional(readOnly = true)
    public List<ChatRoom> getChatRoomsForUser(String userId) {
        return chatRoomRepository.findByUser1IdOrUser2IdOrderByCreatedAtDesc(userId, userId);
    }

    @Transactional
    public ChatMessage saveChatMessage(String chatRoomId, String senderId, String receiverId, String messageContent) {
        ChatMessage message = new ChatMessage(chatRoomId, senderId, receiverId, messageContent);
        ChatMessage savedMessage = chatMessageRepository.save(message);

        sendChatUpdateNotification(savedMessage);

        return savedMessage;
    }

    private void sendChatUpdateNotification(ChatMessage message) {
        long unreadCountForReceiver = chatMessageRepository.countByChatRoomIdAndReceiverIdAndIsReadFalse(message.getChatRoomId(), message.getReceiverId());

        ChatRoomDto updateDto = new ChatRoomDto(
                message.getChatRoomId(),
                message.getSenderId(),
                "Unknown",
                (int) unreadCountForReceiver,
                null,
                message.getMessageContent(),
                message.getTimestamp()
        );

        messagingTemplate.convertAndSendToUser(
                message.getReceiverId(),
                "/topic/chat-updates",
                updateDto
        );

        updateDto.setOtherUserId(message.getReceiverId());
        updateDto.setUnreadCount(0);
        messagingTemplate.convertAndSendToUser(
                message.getSenderId(),
                "/topic/chat-updates",
                updateDto
        );
        System.out.println("실시간 채팅방 업데이트 알림 전송 완료: " + message.getChatRoomId());
    }


    @Transactional(readOnly = true)
    public List<ChatMessage> getChatHistory(String chatRoomId) {
        return chatMessageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId);
    }

    @Transactional
    public void markAllMessagesInChatRoomAsRead(String chatRoomId, String userId) {
        int updatedCount = chatMessageRepository.markMessagesAsRead(chatRoomId, userId);
        System.out.println("채팅방 " + chatRoomId + "에서 사용자 " + userId + "가 받은 메시지 " + updatedCount + "개를 읽음으로 표시했습니다.");

        if (updatedCount > 0) {
            String otherUserId = Arrays.stream(chatRoomId.split("_"))
                                       .filter(id -> !id.equals(userId))
                                       .findFirst().orElse(null);

            ChatRoomDto readUpdateDto = new ChatRoomDto(chatRoomId, otherUserId, "", 0, null, "", null);
            messagingTemplate.convertAndSendToUser(userId, "/topic/chat-updates", readUpdateDto);
        }
    }
}
