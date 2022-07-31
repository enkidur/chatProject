package com.chat.socket.controller;

import com.chat.socket.model.dto.ChatMessageDetailDTO;
import com.chat.socket.model.dto.ChatRoomDetailDTO;
import com.chat.socket.model.dto.UserDto;
import com.chat.socket.repository.ChatRoomRepository;
import com.chat.socket.repository.UserRepository;
import com.chat.socket.service.ChatRoomService;
import com.chat.socket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService cs;
    private final ChatRoomService chatRoomService;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @PostMapping("/chatRoom/find")
    public String findRoomByUsername(@RequestBody UserDto userDto) {
        String roomId = chatRoomService.findCharRoom(userDto);
        if (roomId.equals("")) {
            return "채팅 방이 존재하지 않습니다";
        }
        return roomId;
    }

    @PostMapping("/chatRoom/create")
    public String createChatRoom(@RequestBody UserDto userDto) {
        String roomId = chatRoomService.createChatRoom(userDto);
        if (roomId.equals("")) {
            return "채팅 방을 생성하지 못했습니다.";
        }
        return roomId;
    }

    @PostMapping("/chatRoom/findAll")
    public List<ChatRoomDetailDTO> findAllRoom(@RequestBody UserDto userDto) {
        List<ChatRoomDetailDTO> chatRooms = chatRoomService.findAllChatRoom(userDto);
//        if (chatRoom.size() == 0) {
//            throw new IllegalArgumentException("채팅 방이 존재하지 않습니다");
//        }
        return chatRooms;
    }

    @GetMapping("/chatRoom/{roomId}")
    public List<ChatMessageDetailDTO> findChats(@PathVariable("roomId") String roomId) {
        List<ChatMessageDetailDTO> chats = chatRoomService.findChat(roomId);
        //        if (chats.size() == 0) {
//            throw new IllegalArgumentException("채팅 방이 존재하지 않습니다");
//        }
        return chats;
    }
}
