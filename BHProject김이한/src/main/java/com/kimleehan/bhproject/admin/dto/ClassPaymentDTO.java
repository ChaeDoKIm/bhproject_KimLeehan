package com.kimleehan.bhproject.admin.dto;

import com.kimleehan.bhproject.user.teacher.dto.ScheduleEntity;
import lombok.Data;

@Data
public class ClassPaymentDTO {

    private Long clPayId;           // 학습신청 결제 id
    private int clPayPrice;         // 총가격
    private String clStatus;        // 결제 상태

    private Long clReqId;           // 학습신청 id
    private String pId;             // 학습신청한 학부모 id
    private Long sId;               // 학생 id
    private String sName;           // 학생이름
    private String tName;           // 선생님 성함
    private String planDay;         // 요일
    private String planStartTime;   // 시작 시간
    private String planEndTime;     // 끝나는 시간

    public static ClassPaymentDTO toDTO(ClassPaymentEntity paymentEntity, ScheduleEntity entity) {
        ClassPaymentDTO dto = new ClassPaymentDTO();

        dto.setClPayId(paymentEntity.getClPayId());
        dto.setClPayPrice(paymentEntity.getClPayPrice());
        dto.setClStatus(paymentEntity.getClStatus());

        dto.setClReqId(entity.getClReqId().getClReqId());
        dto.setSId(entity.getSId().getSId());
        dto.setSName(entity.getSId().getSName());
        dto.setTName(entity.getTId().getTName());
        dto.setPlanDay(entity.getPlanDay());
        dto.setPlanStartTime(entity.getPlanStartTime());
        dto.setPlanEndTime(entity.getPlanEndTime());
        dto.setPId(entity.getSId().getPId().getPId());

        return dto;
    }


//
//    public static ClassPaymentDTO toDTO(ClassPaymentEntity paymentEntity, ScheduleEntity entity) {
//        ClassPaymentDTO dto = new ClassPaymentDTO();
//
//        // 🔹 paymentEntity 가 null일 수 있으니 먼저 체크
//        if (paymentEntity != null) {
//            dto.setClPayId(paymentEntity.getClPayId());
//            dto.setClPayPrice(paymentEntity.getClPayPrice());
//            dto.setClStatus(paymentEntity.getClStatus());
//        } else {
//            // 결제 정보가 아직 없는 경우라면 상태를 "결제 전" 같이 기본값으로 둘 수도 있음
//            // dto.setClStatus("결제 전");
//        }
//
//        // 🔹 ScheduleEntity 는 null 아니라고 가정하고 그대로 사용
//        dto.setClReqId(entity.getClReqId().getClReqId());
//        dto.setSId(entity.getSId().getSId());
//        dto.setSName(entity.getSId().getSName());
//        dto.setTName(entity.getTId().getTName());
//        dto.setPlanDay(entity.getPlanDay());
//        dto.setPlanStartTime(entity.getPlanStartTime());
//        dto.setPlanEndTime(entity.getPlanEndTime());
//        dto.setPId(entity.getSId().getPId().getPId());
//
//        return dto;
//    }

}
