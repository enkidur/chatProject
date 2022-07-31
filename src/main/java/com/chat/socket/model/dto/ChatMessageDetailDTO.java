package com.chat.socket.model.dto;

import com.chat.socket.model.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDetailDTO {

    private Long chatId;

    private String roomId;
    private String writer;
    private String message;
    public static ChatMessageDetailDTO toChatMessageDetailDTO(ChatMessage chatMessage){
        ChatMessageDetailDTO chatMessageDetailDTO = new ChatMessageDetailDTO();

        chatMessageDetailDTO.setChatId(chatMessage.getId());
        chatMessageDetailDTO.setRoomId(chatMessage.getChatRoom().getRoomId());

        chatMessageDetailDTO.setWriter(chatMessage.getWriter());
        chatMessageDetailDTO.setMessage(chatMessage.getMessage());

        return chatMessageDetailDTO;

    }
}