package com.kimleehan.bhproject.admin.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "RESUME_REQUEST")
public class ResumeRequestEntity {
    @Id
    @Column(name = "RESID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동생성 수정
    private Long resId;             // 입사지원 ID

    @Nationalized
    @Column(name = "RESPASS")
    private String resPass;         // 지원서 확인을 위한 비회원 비밀번호

    @Nationalized
    @Column(name = "RESNAME")
    private String resName;         // 지원자 성함

    @Column(name = "RESAGE")        
    private Long resAge;            // 지원자 나이

    @Nationalized
    @Column(name = "RESPHONE")
    private String resPhone;        // 지원자 연락처

    @Nationalized
    @Column(name = "RESEMAIL")
    private String resEmail;        // 지원자 이메일

    @Nationalized
    @Column(name = "RESRESTDAY")
    private String resRestDay;      // 휴무일 결정

    @Nationalized
    @Column(name = "RESFILENAME")
    private String resFileName;     // 이력서 파일명

    @Lob
    @Column(name = "RESCOVERLETTER")
    private String resCoverLetter;  // 자소서 (사이트에 직접 입력)

    @Nationalized
    @Column(name = "RESSTATUS")
    private String resStatus;       // 입사지원서 확인 상태

    // TODO: 맡을/희망 과목 및 희망 연령대 추가하기


    public static ResumeRequestEntity toEntity(ResumeRequestDTO dto) {
        ResumeRequestEntity entity = new ResumeRequestEntity();


        entity.setResPass(dto.getResPass());
        entity.setResName(dto.getResName());
        entity.setResAge(dto.getResAge());
        entity.setResPhone(dto.getResPhone());
        entity.setResEmail(dto.getResEmail());
        entity.setResFileName(dto.getResFileName());
        entity.setResCoverLetter(dto.getResCoverLetter());
        entity.setResStatus(dto.getResStatus());

        return entity;
    }

}