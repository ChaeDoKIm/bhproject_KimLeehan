package com.kimleehan.bhproject.admin.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ClassReqPaymentDTO {

    private Long clPayId;           // 학습신청 결제 id
    private BigDecimal clPayPrice;  // 총가격
    private String clStatus;        // 결제 상태
    private Long clReqId;           // 학습신청 id\
    private Instant clReqDate;          // 학습신청일
    private String clReqPlace;          // 집(혹은 원하는 장소)
    private String clReqDay;            // 요일
    private String clReqLevel;          // 학습 레벨
    private String clReqSubject;        // 학습할 과목
    private String clReqStatus;         // 신청서 확인 상태
    private Long sId;                 // 학생 id
    private String pId;                 // 학부모 id
    private String tId;                 // 학부모가 요청하신 선생님 id

    private Long planId;                    // 일정 id
    private Long planStartTime;             // 일정 시작
    private Long planEndTime;               // 일정 끝
    private String planDay;              // 요일
    private String planPlace;               // 장소
    private String planStatus;              // 일정 완료 상태

}
