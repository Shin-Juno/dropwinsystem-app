package com.dropwinsystem.app.repository;

import com.dropwinsystem.app.domain.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoomIdOrderByTimestampAsc(String chatRoomId);

    List<ChatMessage> findByChatRoomIdOrderByTimestampDesc(String chatRoomId, Pageable pageable);

    long countByChatRoomIdAndReceiverIdAndIsReadFalse(String chatRoomId, String receiverId);

    @Modifying
    @Query("UPDATE ChatMessage m SET m.isRead = TRUE WHERE m.chatRoomId = :chatRoomId AND m.receiverId = :receiverId AND m.isRead = FALSE")
    int markMessagesAsRead(@Param("chatRoomId") String chatRoomId, @Param("receiverId") String receiverId);

    List<ChatMessage> findByChatRoomIdAndReceiverIdAndIsReadFalse(String chatRoomId, String receiverId);
}
