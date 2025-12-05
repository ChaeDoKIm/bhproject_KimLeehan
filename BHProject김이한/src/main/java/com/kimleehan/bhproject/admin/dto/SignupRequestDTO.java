package com.kimleehan.bhproject.admin.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SignupRequestDTO {

    private Long sReqId;                // 상담 신청서 id
    private String sReqName;            // 상담 신청자 성함 //TODO : 학부모 넣을시 제거
    private String sReqPhone;           // 신청자 연락처    //TODO : 학부모 넣을시 제거
    private String sReqEmail;           // 신청자 이메일    //TODO : 학부모 넣을시 제거
    private String sReqType;            // 원하는 상담 유형
    private LocalDate sReqDate;         // 상담 가능한 날짜
    //private Long sReqStartTime;         // 상담 가능 시간대 (시작)
    //private Long sReqEndTime;           // 상담 가능 시간대 (끝)
    private String sReqStatus;          // 상담신청 확인 상태


    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime sReqStartTime;         // 상담 가능 시간대 (시작)
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime sReqEndTime;           // 상담 가능 시간대 (끝)

    private String pId;                 // 상담신청한 학부모

    public static SignupRequestDTO toDTO (SignupRequestEntity entity) {
        SignupRequestDTO dto = new SignupRequestDTO();

        dto.setSReqId(entity.getSReqId());
        dto.setSReqName(entity.getSReqName());      //TODO : 학부모 넣을시 제거
        dto.setSReqPhone(entity.getSReqPhone());    //TODO : 학부모 넣을시 제거
        dto.setSReqEmail(entity.getSReqEmail());    //TODO : 학부모 넣을시 제거
        dto.setSReqType(entity.getSReqType());
        dto.setSReqDate(entity.getSReqDate());
        dto.setSReqStartTime(entity.getSReqStartTime());
        dto.setSReqEndTime(entity.getSReqEndTime());
        dto.setSReqStatus(entity.getSReqStatus());

//        dto.setPId(entity.getPId().getPId());

        dto.setSReqDate(entity.getSReqDate());


        // ✅ 여기만 이렇게 수정
        if (entity.getPId() != null) {
            dto.setPId(entity.getPId().getPId());   // 로그인 학부모 ID
        } else {
            dto.setPId(null);                       // 익명 상담
        }


        return dto;
    }


}
