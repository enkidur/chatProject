package com.chat.socket.controller;

import com.chat.socket.model.ChatRoom;
import com.chat.socket.model.User;
import com.chat.socket.model.dto.ChatRoomDTO;
import com.chat.socket.model.dto.ChatRoomDetailDTO;
import com.chat.socket.model.dto.UserDto;
import com.chat.socket.repository.ChatRoomRepository;
import com.chat.socket.repository.UserRepository;
import com.chat.socket.service.ChatRoomService;
import com.chat.socket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        if (!roomId.equals("")) {
            return "채팅 방이 존재하지 않습니다";
        }
        return roomId;
    }
//        String username = userDto.getUsername();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));
//        ChatRoom chatRoom = chatRoomRepository.findByUser(user)
//                .orElseThrow(() -> new RuntimeException("채팅 방을 찾을 수 없습니다"));

    @PostMapping("/chatRoom/create")
    public String createChatRoom(@RequestParam("roomName") String roomName,
                                 @RequestBody UserDto userDto) {
        String roomId = chatRoomService.createChatRoom(userDto, roomName);
        if (!roomId.equals("")) {
            return "채팅 방을 생성하지 못했습니다.";
        }
        return roomId;
    }
    // 유저를 위한 채팅룸
//    ChatRoomDTO UserChatRoomDTO = new ChatRoomDTO(roomName, userDto.getParticipants());
//    ChatRoom UserRoom = new ChatRoom(UserChatRoomDTO);
//    User user = userRepository.findByUsername(userDto.getUsername())
//            .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));
//        UserRoom.setChatRoom(user);
//
//    //초대한 친구를 위한 채팅룸
//    ChatRoomDTO ParticipantsChatRoomDTO = new ChatRoomDTO(roomName, userDto.getUsername());
//    ChatRoom ParticipantsRoom = new ChatRoom(ParticipantsChatRoomDTO);
//    User participants = userRepository.findByUsername(userDto.getParticipants())
//            .orElseThrow(() -> new RuntimeException("친구를 찾을 수 없습니다"));
//        ParticipantsRoom.setUser(participants);
//
//        chatRoomRepository.save(UserRoom);
//        chatRoomRepository.save(ParticipantsRoom);
}
