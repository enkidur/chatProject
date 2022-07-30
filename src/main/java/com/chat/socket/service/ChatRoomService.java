package com.chat.socket.service;

import com.chat.socket.model.ChatRoom;
import com.chat.socket.model.User;
import com.chat.socket.model.dto.ChatRoomDTO;
import com.chat.socket.model.dto.UserDto;
import com.chat.socket.repository.ChatRoomRepository;
import com.chat.socket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatService cs;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public String findCharRoom(UserDto userDto) {
        String username = userDto.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));
        ChatRoom chatRoom = chatRoomRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("채팅 방을 찾을 수 없습니다"));

        return chatRoom.getRoomId();
    }

    public String createChatRoom(UserDto userDto, String roomName) {
        // 유저를 위한 채팅룸
        ChatRoomDTO UserChatRoomDTO = new ChatRoomDTO(roomName, userDto.getParticipants());
        ChatRoom UserRoom = new ChatRoom(UserChatRoomDTO);
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));
        UserRoom.setChatRoom(user);

        //초대한 친구를 위한 채팅룸
        ChatRoomDTO ParticipantsChatRoomDTO = new ChatRoomDTO(roomName, userDto.getUsername());
        ChatRoom ParticipantsRoom = new ChatRoom(ParticipantsChatRoomDTO);
        User participants = userRepository.findByUsername(userDto.getParticipants())
                .orElseThrow(() -> new RuntimeException("친구를 찾을 수 없습니다"));
        ParticipantsRoom.setUser(participants);

        // 생성한 채팅 룸을 저장한다.
        chatRoomRepository.save(UserRoom);
        chatRoomRepository.save(ParticipantsRoom);

        //저장에 성공했으면 룸 ID를 리턴
        return UserRoom.getRoomId();
    }
}
