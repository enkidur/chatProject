package com.chat.socket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {

    private String participants;
    private String roomId;
    private String name;

    private Set<WebSocketSession> sessions = new HashSet<>();
    //WebSocketSession 은 Spring 에서 Websocket Connection 이 맺어진 세션
    public  ChatRoomDTO (String name, String participants){
        this.roomId = UUID.randomUUID().toString();
        this.name = name;
        this.participants = participants;
    }
}
