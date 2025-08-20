package com.dropwinsystem.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomInfo {
    private String chatRoomId;     
    private String otherUserId;    
    private String otherUserName;  
    private String lastMessage;    
    private String lastMessageTime;
    private int unreadCount;       
}