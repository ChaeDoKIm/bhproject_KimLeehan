package com.kimleehan.bhproject.admin.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeacherSpecDTO {

    private Long tSid;               // 추가 자격증 id
    private String tId;              // 선생님 id
    private String specName;         // 자격증 이름
    private MultipartFile specFile;  // 자격인증 파일
    private String specFileName;     // 파일명
    private String specStatus;       // 확인상태

    public static TeacherSpecDTO toDTO(TeacherSpecEntity entity) {
        TeacherSpecDTO dto = new TeacherSpecDTO();

        dto.setTSid(entity.getTSid());
        dto.setTId(entity.getTId());
        dto.setSpecName(entity.getSpecName());
        dto.setSpecFileName(entity.getSpecFileName());
        dto.setSpecStatus(entity.getSpecStatus());

        return dto;
    }
}
