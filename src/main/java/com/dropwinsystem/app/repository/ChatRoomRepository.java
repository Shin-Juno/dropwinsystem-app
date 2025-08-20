package com.dropwinsystem.app.repository;

import com.dropwinsystem.app.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    List<ChatRoom> findByUser1IdOrUser2IdOrderByCreatedAtDesc(String user1Id, String user2Id);
}
