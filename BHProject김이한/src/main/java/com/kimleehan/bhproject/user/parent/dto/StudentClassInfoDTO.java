package com.kimleehan.bhproject.user.parent.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentClassInfoDTO {
    
    private Long sId;                   // 학생 id (검색용)
    private String sName;               // 학생 이름
    private String sGrade;              // 학년

    private String tName;               // 선생님 성함
    private String tId;                 // 선생님 id

    private Long clProgId;              // 진행학습 id
    private String clProgSubject;       // 과목
    private String clProgBook;          // 교재
    private String clProgStatus;        // 수강 상태
    private Double clProgPercent;       // 수강 진행도
    private LocalDate clProgStartDate;  // 시작일
    private LocalDate clProgEndDate;    // 종료일

    
    private String cProgress;           // 학습 진행도 (몇 주차)
    private String cPage;               // 학습 진도 (몇 페이지)
    private String cClassContents;      // 학습 내용
    private String cContents;           // 학습에 관한 선생님 코멘트

}
