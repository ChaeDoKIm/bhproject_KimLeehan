package com.kimleehan.bhproject.user.teacher.dto;

import lombok.Data;

@Data
public class TeacherClassInfoDTO {

    private Long clProgId;          // 학생 id
    private Long sId;
    private String sName;           // 학생 이름
    private String sGender;         // 학생 성별
    private String sGrade;          // 학년
    private int cProgress;          // 몇주차
    private String clProgDay;       // 학습 진행 요일
    private String tId;             // 선생님 id
    private String clProgStatus;    // 진행 상태
    private Double clProgPercent;   // 진행률
    private String clProgSubject;   // 과목
    private String clProgBook;      // 교재
    private String clProgLevel;     // 학습 레벨


    public static TeacherClassInfoDTO toDTO(ClassProgressEntity entity) {
        TeacherClassInfoDTO dto = new TeacherClassInfoDTO();

        dto.setClProgDay(entity.getClProgDay());
        dto.setClProgId(entity.getClProgId());
        dto.setSId(entity.getSId().getSId());
        dto.setSName(entity.getSId().getSName());
        dto.setSGender(entity.getSId().getSGender());
        dto.setSGrade(entity.getSId().getSGrade());
        dto.setTId(entity.getTId().getTId());
        dto.setClProgStatus(entity.getClProgStatus());
        dto.setClProgPercent(entity.getClProgPercent());
        dto.setClProgSubject(entity.getClProgSubject());
        dto.setClProgBook(entity.getClProgBook());
        dto.setClProgLevel(entity.getClProgLevel());

        return dto;
    }

}
