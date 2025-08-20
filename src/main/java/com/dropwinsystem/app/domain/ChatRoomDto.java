package com.dropwinsystem.app.domain;

import java.time.LocalDateTime;

public class ChatRoomDto {
    private String chatRoomId;
    private String otherUserId;  
    private String otherUserName;
    private int unreadCount;
    private LocalDateTime createdAt;
    private String lastMessage;
    private LocalDateTime lastMessageTimestamp;

    public ChatRoomDto(String chatRoomId, String otherUserId, String otherUserName, int unreadCount,
                       LocalDateTime createdAt, String lastMessage, LocalDateTime lastMessageTimestamp) {
        this.chatRoomId = chatRoomId;
        this.otherUserId = otherUserId;
        this.otherUserName = otherUserName;
        this.unreadCount = unreadCount;
        this.createdAt = createdAt;
        this.lastMessage = lastMessage;
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public ChatRoomDto() {
    }

    public String getChatRoomId() { return chatRoomId; }
    public void setChatRoomId(String chatRoomId) { this.chatRoomId = chatRoomId; }

    public String getOtherUserId() { return otherUserId; }
    public void setOtherUserId(String otherUserId) { this.otherUserId = otherUserId; }

    public String getOtherUserName() { return otherUserName; }
    public void setOtherUserName(String otherUserName) { this.otherUserName = otherUserName; }

    public int getUnreadCount() { return unreadCount; }
    public void setUnreadCount(int unreadCount) { this.unreadCount = unreadCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getLastMessage() { return lastMessage; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }

    public LocalDateTime getLastMessageTimestamp() { return lastMessageTimestamp; }
    public void setLastMessageTimestamp(LocalDateTime lastMessageTimestamp) { this.lastMessageTimestamp = lastMessageTimestamp; }

    public String getFormattedLastMessageTime() {
        if (lastMessageTimestamp == null) {
            return "";
        }

        return lastMessageTimestamp.format(java.time.format.DateTimeFormatter.ofPattern("M월 d일 a h:mm"));
    }
}