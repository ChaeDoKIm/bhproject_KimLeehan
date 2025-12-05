package com.kimleehan.bhproject.user.teacher.dto;

import lombok.Data;

@Data
public class SchedulesDTO {

    private Long planId;                    // 일정 id
    private String planStartTime;           // 일정 시작
    private String planEndTime;             // 일정 끝
    private String planDay;                 // 요일
    private String planPlace;               // 장소
    private String planStatus;              // 일정 완료 상태
    private String sName;                   // 수업 받는 학생 이름

    private String tId;                     // 담당 선생님 id
    private String tName;
    private Long sId;                       // 수업 받는 학생 id
    private Long clReqId;                   // 학습 신청 아이디

    public static SchedulesDTO toDTO (ScheduleEntity entity) {
        SchedulesDTO dto = new SchedulesDTO();

        dto.setPlanId(entity.getPlanId());
        dto.setPlanStartTime(entity.getPlanStartTime());
        dto.setPlanEndTime(entity.getPlanEndTime());
        dto.setPlanDay(entity.getPlanDay());
        dto.setPlanPlace(entity.getPlanPlace());
        dto.setPlanStatus(entity.getPlanStatus());

        dto.setTId(entity.getTId().getTId());
        dto.setTName(entity.getTId().getTName());
        dto.setSId(entity.getSId().getSId());
        dto.setClReqId(entity.getClReqId().getClReqId());

        return dto;
    }
}
