package com.kimleehan.bhproject.admin.dto;

import com.kimleehan.bhproject.user.parent.dto.ParentInfoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "SIGNUP_REQUEST")
public class SignupRequestEntity {
    @Id
    @Column(name = "SREQID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동생성 수정
    private Long sReqId;        // 상담 신청서 ID

    // TODO: 학부모 로그인 할 거면 제거
    @Nationalized
    @Column(name = "SREQNAME", length = 100)
    private String sReqName;    // 신청자명

    // TODO: 학부모 로그인 할 거면 제거
    @Nationalized
    @Column(name = "SREQPHONE", length = 20)
    private String sReqPhone;   // 연락처

    // TODO: 학부모 로그인 할 거면 제거
    @Nationalized
    @Column(name = "SREQEMAIL", length = 100)
    private String sReqEmail;   // 이메일

    @Nationalized
    @Column(name = "SREQTYPE", length = 50)
    private String sReqType;    // 상담유형

    @Column(name = "SREQDATE")
    @Temporal(TemporalType.DATE)
    private LocalDate sReqDate; // 신청일
//
//    @Column(name = "SREQSTARTTIME")
//    private Long sReqStartTime; // 희망시간대(시작)
//
//    @Column(name = "SREQENDTIME")
//    private Long sReqEndTime;   // 희망시간대(끝)


    @Column(name = "SREQSTARTTIME")
    private LocalDateTime sReqStartTime; // 희망시간대(시작)

    @Column(name = "SREQENDTIME")
    private LocalDateTime sReqEndTime;   // 희망시간대(끝)


    @Nationalized
    @Column(name = "SREQSTATUS", length = 50)
    private String sReqStatus;  // 신청서 확인 상태
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "PID")
//    private ParentInfoEntity pId;   // 신청한 학부모


    @ManyToOne(optional = true, fetch = FetchType.LAZY)  // ✅ optional = true
    @JoinColumn(name = "PID", nullable = true)           // ✅ nullable = true
    private ParentInfoEntity pId;



    public static SignupRequestEntity toEntity(SignupRequestDTO dto) {
        SignupRequestEntity entity = new SignupRequestEntity();

        entity.setSReqId(dto.getSReqId());
        entity.setSReqName(dto.getSReqName());   // TODO: 학부모 넣을시 제거
        entity.setSReqPhone(dto.getSReqPhone()); // TODO: 학부모 넣을시 제거
        entity.setSReqEmail(dto.getSReqEmail()); // TODO: 학부모 넣을시 제거
        entity.setSReqType(dto.getSReqType());
        entity.setSReqStartTime(dto.getSReqStartTime());
        entity.setSReqEndTime(dto.getSReqEndTime());
        entity.setSReqStatus(dto.getSReqStatus());
        entity.setSReqDate(dto.getSReqDate());

        ParentInfoEntity parent = new ParentInfoEntity();
        parent.setPId(dto.getPId());
//        entity.setPId(parent);


        return entity;
    }

}