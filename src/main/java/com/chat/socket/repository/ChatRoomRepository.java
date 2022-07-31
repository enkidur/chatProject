package com.chat.socket.repository;

import com.chat.socket.model.ChatRoom;
import com.chat.socket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomId(String roomId);
    Optional<ChatRoom> findByUser_UsernameAndAndParticipants(String username, String participants);
    List<ChatRoom> findAllByUser_Username(String username);
}
