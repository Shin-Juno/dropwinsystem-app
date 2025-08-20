package com.dropwinsystem.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chatroom") 
public class ChatRoom {

    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "user1_id", nullable = false, length = 255)
    private String user1Id;

    @Column(name = "user2_id", nullable = false, length = 255)
    private String user2Id;

    @Column(name = "created_at", nullable = false, updatable = false) 
    private LocalDateTime createdAt;

    @Column(name = "last_message_id")
    private Long lastMessageId;

    public ChatRoom(String id, String user1Id, String user2Id) {
        this.id = id;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.createdAt = LocalDateTime.now();
    }
}
