package com.kimleehan.bhproject.chat.model;


import lombok.*;

import java.time.LocalDateTime;

@Data
public class MessageDTO{
    private Long id;  // 메시지 ID
    private String senderName;      // 보낸 사람
    private String receiverName;    // 받는 사람
    private String message;         // 메시지 내용
    private LocalDateTime date;     // 메시지 전송시간
    private Status status;          // 메시지 상태


    // Entity → DTO 변환
    public static MessageDTO fromEntity(MessageEntity entity) {
        MessageDTO dto = new MessageDTO();
        dto.setId(entity.getId());
        dto.setSenderName(entity.getSenderName());
        dto.setReceiverName(entity.getReceiverName());
        dto.setMessage(entity.getMessage());
        dto.setDate(entity.getDate());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    // DTO → Entity 변환
    public static MessageEntity toEntity(MessageDTO dto) {
        MessageEntity entity = new MessageEntity();
        entity.setId(dto.getId());
        entity.setSenderName(dto.getSenderName());
        entity.setReceiverName(dto.getReceiverName());
        entity.setMessage(dto.getMessage());
        entity.setDate(dto.getDate());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    // MessageEntity → MessageDTO 변환 메서드 추가
    public static MessageDTO toDTO(MessageEntity entity) {
        MessageDTO dto = new MessageDTO();
        dto.setId(entity.getId());
        dto.setSenderName(entity.getSenderName());
        dto.setReceiverName(entity.getReceiverName());
        dto.setMessage(entity.getMessage());
        dto.setDate(entity.getDate());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
