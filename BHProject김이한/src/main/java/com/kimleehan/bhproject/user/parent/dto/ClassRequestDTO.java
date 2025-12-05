package com.kimleehan.bhproject.user.parent.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ClassRequestDTO {
    private Long clReqId;               // 학습신청 id
    private Instant clReqDate;          // 학습신청일
    private String clReqPlace;          // 집(혹은 원하는 장소)
    private String clReqDay;            // 요일
    private String clReqLevel;          // 학습 레벨
    private String clReqSubject;        // 학습할 과목
    private String clReqStatus;         // 신청서 확인 상태
    private Long sId;                   // 학생 id
    private String sName;               // 학생 이름
    private String pId;                 // 학부모 id
    private String tId;                 // 학부모가 요청하신 선생님 id
    private String tName;

    public ClassRequestDTO(String clReqPlace, Long sId, Long clReqId) {
        this.clReqPlace = clReqPlace;
        this.sId = sId;
        this.clReqId = clReqId;
    }

    public ClassRequestDTO() {

    }

    public static ClassRequestDTO toDTO (ClassRequestEntity entity) {
        ClassRequestDTO dto = new ClassRequestDTO();

        dto.setClReqDate(entity.getClReqDate());
        dto.setClReqPlace(entity.getClReqPlace());
        dto.setClReqDay(entity.getClReqDay());
        dto.setClReqLevel(entity.getClReqLevel());
        dto.setClReqSubject(entity.getClReqSubject());
        dto.setClReqStatus(entity.getClReqStatus());

        dto.setClReqId(entity.getClReqId());

        dto.setTId(entity.getTId().getTId());
        dto.setSId(entity.getSId().getSId());
        dto.setPId(entity.getPId().getPId());
        dto.setSName(entity.getSId().getSName());
        dto.setTName(entity.getTId().getTName());

        return dto;
    }

}
