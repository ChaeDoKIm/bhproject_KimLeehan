package com.kimleehan.bhproject.user.teacher.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TeacherCommentDTO {

    private Long cId;               // 코멘트 id
    private LocalDateTime cDate;    // 작성일
    private String cProgress;       // 코멘트 내용 : 학습 진도(몇주차)
    private String cPage;           // 나간 진도(몇 페이지)
    private String cClassContents;  // 수업 내용 (예: 현재완료형, 과거분사, 단어시험)
    private String cContents;       // 코멘트 내용 : 학부모님에게 전할 말

    private Long clProgId;          // 진행학습 id : 학생 id, 과목, 선생님 id

    public static TeacherCommentDTO toDTO(TeacherCommentEntity entity) {
        TeacherCommentDTO dto = new TeacherCommentDTO();

        dto.setCId(entity.getCId());
        dto.setCDate(entity.getCDate());
        dto.setCProgress(entity.getCProgress());
        dto.setCPage(entity.getCPage());
        dto.setCContents(entity.getCContents());
        dto.setCClassContents(entity.getCClassContents());

        dto.setClProgId(entity.getClProgId().getClProgId());

        return dto;
    }
}
