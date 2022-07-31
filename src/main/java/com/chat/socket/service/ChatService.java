package com.chat.socket.service;


import com.chat.socket.model.ChatMessage;
import com.chat.socket.model.ChatRoom;
import com.chat.socket.model.dto.ChatMessageDetailDTO;
import com.chat.socket.model.dto.ChatMessageSaveDTO;
import com.chat.socket.repository.ChatRepository;
import com.chat.socket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    private final ChatRepository cr;
    private final ChatRoomRepository crr;
    @Transactional
    public void enterChatRoom(ChatMessageSaveDTO message) {
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");

        List<ChatMessage> chatList = cr.findAllByChatRoom_roomId(message.getRoomId());
        if (chatList != null) {
            for (ChatMessage c : chatList) {
                message.setWriter(c.getWriter());
                message.setMessage(c.getMessage());
            }
        }
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

        ChatRoom chatRoom = crr.findByRoomId(message.getRoomId())
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다"));
        cr.save(ChatMessage.toChatEntity(message, chatRoom));
    }
    @Transactional
    public void sendChat(ChatMessageSaveDTO message) {
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

        // DB에 채팅내용 저장
        ChatRoom chatRoom = crr.findByRoomId(message.getRoomId())
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다"));
        ChatMessageSaveDTO chatMessageSaveDTO = new ChatMessageSaveDTO(message.getRoomId(),message.getWriter(), message.getMessage());
        cr.save(ChatMessage.toChatEntity(chatMessageSaveDTO,chatRoom));
    }
}
