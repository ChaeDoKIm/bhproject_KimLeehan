package com.kimleehan.bhproject.user.teacher.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClassProgressDTO {

    private Long clProgId;               // 진행된 학습 id
    private String clProgSubject;        // 과목
    private String clProgBook;           // 사용하는 교재
    private LocalDate clProgStartDate;   // 학습 시작일
    private LocalDate clProgEndDate;     // 학습 종료일
    private Double clProgPercent;        // 학습 진행률 %
    private String clProgStatus;         // 학습 진행상태 (예정, 진행중, 종료, 중단/중도포기)
    private String clProgDay;            // 학습 진행 요일

    private Long sId;                    // 학습 듣는 학생 id
    private String tId;                  // 학습 진행하는 선생님 id

    public static ClassProgressDTO toDTO(ClassProgressEntity entity) {
        ClassProgressDTO dto = new ClassProgressDTO();

        dto.setClProgId(entity.getClProgId());
        dto.setClProgSubject(entity.getClProgSubject());
        dto.setClProgBook(entity.getClProgBook());
        dto.setClProgStartDate(entity.getClProgStartDate());
        dto.setClProgEndDate(entity.getClProgEndDate());
        dto.setClProgPercent(entity.getClProgPercent());
        dto.setClProgStatus(entity.getClProgStatus());
        dto.setClProgDay(entity.getClProgDay());

        dto.setSId(entity.getSId().getSId());
        dto.setTId(entity.getTId().getTId());

        return dto;
    }

}
