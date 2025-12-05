package com.kimleehan.bhproject.chat.model;


public enum Status {
    JOIN,
    MESSAGE,
    LEAVE,
    SENT,

    //새로 추가
    TYPING,       // 입력 중
    STOP_TYPING   // 입력 중 종료
}
