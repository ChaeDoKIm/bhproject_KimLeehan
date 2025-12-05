package com.kimleehan.bhproject.admin.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class ResumeRequestDTO {

    private Long resId;              // 입사지원 ID
    private String resPass;         // 비회원 확인용 비밀번호
    private String resName;         // 지원자 성함
    private Long resAge;             // 지원자 나이
    private String resPhone;        // 지원자 연락처
    private String resEmail;        // 지원자 이메일
    private String resRestDay;      // 휴무일 결정
    private MultipartFile resFile;  // 이력서
    private String resFileName;     // 이력서 파일명
    private String resCoverLetter;  // 자소서 OR 입사동기 (페이지에 직접 입력)
    private String resStatus;       // 지원서 확인 상태

    // TODO: 맡을/희망 과목 및 희망 연령대 추가하기

    public static ResumeRequestDTO toDTO(ResumeRequestEntity entity) {
        ResumeRequestDTO dto = new ResumeRequestDTO();

        dto.setResId(entity.getResId());
        dto.setResPass(entity.getResPass());
        dto.setResName(entity.getResName());
        dto.setResAge(entity.getResAge());
        dto.setResPhone(entity.getResPhone());
        dto.setResEmail(entity.getResEmail());
        dto.setResRestDay(entity.getResRestDay());
        dto.setResFileName(entity.getResFileName());
        dto.setResCoverLetter(entity.getResCoverLetter());
        dto.setResStatus(entity.getResStatus());

        return dto;
    }

}
