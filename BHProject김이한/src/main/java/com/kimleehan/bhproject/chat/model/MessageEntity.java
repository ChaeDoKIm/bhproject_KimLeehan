package com.kimleehan.bhproject.chat.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="CHAT")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 ID
    @Column(name = "ID")
    private Long id; // 메시지 ID

    @Column(name = "SENDER_NAME", nullable = false)
    private String senderName; // 보낸 사람 이름

    @Column(name = "RECEIVER_NAME", nullable = false)
    private String receiverName; // 받는 사람 이름

    @Column(name = "MESSAGE",nullable = false)
    private String message; // 메시지 내용

    @CreationTimestamp
    @Column(name = "CHAT_DATE", nullable = false, updatable = false)
    private LocalDateTime date; // 메시지 보낸 시간 (자동 생성)

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status; // 메시지 상태 (예: MESSAGE, JOIN, LEAVE)

}
