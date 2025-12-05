package com.kimleehan.bhproject.user.teacher.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeacherInfoDTO {

    private String tId;                     // 선생님 id; 선생님 수정 불가
    private String tPass;                   // 비밀번호
    private String tName;                   // 선생님 성함; 선생님 수정 불가
    private String tPhone;                  // 연락처
    private String tEmail;                  // 이메일
    private String tArea;                   // 활동 가능한 지역; 선생님 수정 불가
//    private Long tStartTime;                // 활동 시간대 (시작); 선생님 수정 불가
//    private Long tEndTime;                  // 활동 시간대 (끝); 선생님 수정 불가
    private String tGrade;                   // 맡을 학년; 선생님 수정 불가
    private String tLevel;                  // 수업 레벨 (초, 중/고, 심화)
    private String tSubject;                // 맡을 과목
    private String tRestDay;                // 휴무일
    private String tEdu;                    // 최종 학력; 선생님 수정 불가
    private MultipartFile tProfile;         // 프로필 사진
    private String tProfileName;            // 프로필 사진 파일명
    private String tDesc;                   // 자기소개
//    private String tBook;                   // 주 사용 교재
//    private MultipartFile tBookCover;       // 교재 커버
//    private String tBookCoverName;          // 교재 커버 파일명
    private String tGender;                 // 성별; 선생님 수정 불가

    // 기본 생성자 (필수)
    public TeacherInfoDTO() {
    }

    // 매개변수 생성자 (필요한 필드만 포함)
    public TeacherInfoDTO(String tId, String tName) {
        this.tId = tId;
        this.tName = tName;
    }


    public static TeacherInfoDTO toDTO(TeacherInfoEntity entity) {
        TeacherInfoDTO dto = new TeacherInfoDTO();

        dto.setTId(entity.getTId());
        dto.setTPass(entity.getTPass());
        dto.setTName(entity.getTName());
        dto.setTPhone(entity.getTPhone());
        dto.setTEmail(entity.getTEmail());
        dto.setTArea(entity.getTArea());
//        dto.setTStartTime(entity.getTStartTime());
//        dto.setTEndTime(entity.getTEndTime());
        dto.setTGrade(entity.getTGrade());
        dto.setTLevel(entity.getTLevel());
        dto.setTSubject(entity.getTSubject());
        dto.setTRestDay(entity.getTRestDay());
        dto.setTEdu(entity.getTEdu());
        dto.setTProfileName(entity.getTProfileName());
        dto.setTDesc(entity.getTDesc());
//        dto.setTBook(entity.getTBook());
//        dto.setTBookCoverName(entity.getTBookCoverName());
        dto.setTGender(entity.getTGender());

        return dto;
    }
}
