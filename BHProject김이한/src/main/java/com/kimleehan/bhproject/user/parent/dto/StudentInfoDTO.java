package com.kimleehan.bhproject.user.parent.dto;

import lombok.Data;

@Data
public class StudentInfoDTO {

    private Long sId;             // 학생 id
    private String sName;           // 학생 이름
    private String sGrade;          // 학생 학년
    private String sGender;         // 성별
    private String pId;             // 학부모 id

    public static StudentInfoDTO toDTO(StudentInfoEntity entity) {
        StudentInfoDTO dto = new StudentInfoDTO();

        dto.setSId(entity.getSId());
        dto.setSName(entity.getSName());
        dto.setSGrade(entity.getSGrade());
        dto.setSGender(entity.getSGender());

        dto.setPId(entity.getPId().getPId());

        return dto;
    }

}
