package com.chat.socket.service;

import com.chat.socket.model.ChatMessage;
import com.chat.socket.model.ChatRoom;
import com.chat.socket.model.User;
import com.chat.socket.model.dto.ChatMessageDetailDTO;
import com.chat.socket.model.dto.ChatRoomDTO;
import com.chat.socket.model.dto.ChatRoomDetailDTO;
import com.chat.socket.model.dto.UserDto;
import com.chat.socket.repository.ChatRepository;
import com.chat.socket.repository.ChatRoomRepository;
import com.chat.socket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatService cs;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    @Transactional
    public String findCharRoom(UserDto userDto) {
        // UserDto을 통해서 유저를 찾는다.
        String username = userDto.getUsername();

        // 유저가 존재하는 지 검증한다.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));

        // 채팅방이 존재하는 지 검증한다.
        ChatRoom chatRoom = chatRoomRepository
                .findByUser_UsernameAndAndParticipants(userDto.getUsername(), userDto.getParticipants())
                .orElseThrow(() -> new RuntimeException("채팅 방을 찾을 수 없습니다"));

        // 모든 검증에서 통과하면 룸 ID를 리턴한다.
        return chatRoom.getRoomId();
    }
    @Transactional
    public String createChatRoom(UserDto userDto) {

        // 유저를 위한 채팅룸
        ChatRoomDTO UserChatRoomDTO = new ChatRoomDTO(userDto.getParticipants(), userDto.getRoomName());
        ChatRoom UserRoom = new ChatRoom(UserChatRoomDTO);
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));
        UserRoom.setChatRoom(user);

        //초대한 친구를 위한 채팅룸
        ChatRoomDTO ParticipantsChatRoomDTO = new ChatRoomDTO(userDto.getUsername(), userDto.getRoomName());
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

    @Transactional
    public List<ChatRoomDetailDTO> findAllChatRoom(UserDto userDto) {
        // Dto를 넣기 위한 리스트
        List<ChatRoomDetailDTO> chatRoomDetailDTOS = new ArrayList<>();

        // 채팅 방을 전부 찾는다.
        List<ChatRoom> roomList = chatRoomRepository.findAllByUser_Username(userDto.getUsername());

        // 채팅방 들을 Dto 에 넣어서 보관한다.
        for (ChatRoom chatRoom : roomList) {
            chatRoomDetailDTOS.add(ChatRoomDetailDTO.toChatRoomDetailDTO(chatRoom));
        }
        return chatRoomDetailDTOS;
    }

    public List<ChatMessageDetailDTO> findChat(String roomId) {
        // 채팅을 찾는다.
        List<ChatMessage> chats = chatRepository.findAllByChatRoom_roomId(roomId);

        // 찾은 채팅을 Dto 로 변환시켜준다.
        List<ChatMessageDetailDTO> chatDto = new ArrayList<>();
        for (ChatMessage chat : chats) {
            chatDto.add(ChatMessageDetailDTO.toChatMessageDetailDTO(chat));
        }
        
        //반환
        return chatDto;
    }
}
