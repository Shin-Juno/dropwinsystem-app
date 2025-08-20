package com.dropwinsystem.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; 

    @Column(name = "chat_room_id", nullable = false, length = 255)
    private String chatRoomId;

    @Column(name = "sender_id", nullable = false, length = 255)
    private String senderId; 

    @Column(name = "receiver_id", nullable = false, length = 255)
    private String receiverId; 

    @Column(name = "message_content", nullable = false, columnDefinition = "TEXT")
    private String messageContent;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp; 

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    public ChatMessage(String chatRoomId, String senderId, String receiverId, String messageContent) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageContent = messageContent;
        this.timestamp = LocalDateTime.now(); 
        this.isRead = false; 
    }
}
