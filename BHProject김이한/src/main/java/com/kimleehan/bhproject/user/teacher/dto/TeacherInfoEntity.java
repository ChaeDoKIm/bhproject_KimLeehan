package com.kimleehan.bhproject.user.teacher.dto;

import com.kimleehan.bhproject.user.parent.dto.ClassRequestEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "TEACHER_INFO")
public class TeacherInfoEntity {
    @Id
    @Nationalized
    @Column(name = "TID", nullable = false, length = 50)
    private String tId;         // 선생님 ID; 선생님 수정 불가

    @Nationalized
    @Column(name = "TPASS", nullable = false)
    private String tPass;       // 비밀번호

    @Nationalized
    @Column(name = "TNAME", nullable = false, length = 100)
    private String tName;       // 선생님 성함; 선생님 수정 불가

    @Nationalized
    @Column(name = "TPHONE", length = 20)
    private String tPhone;      // 연락처

    @Nationalized
    @Column(name = "TEMAIL", length = 100)
    private String tEmail;      // 이메일

    @Nationalized
    @Column(name = "TAREA")
    private String tArea;       // 맡을 지역/장소 (EX. 인천 미추홀구 학익동); 선생님 수정 불가
//
//    @Column(name = "TSTARTTIME")
//    private Long tStartTime;    // 업무 시작; 선생님 수정 불가
//
//    @Column(name = "TENDTIME")
//    private Long tEndTime;      // 업무 끝; 선생님 수정 불가

    @Column(name = "TGRADE")
    private String tGrade;       // 맡을 학년; 선생님 수정 불가

    @Nationalized
    @Column(name = "TLEVEL")
    private String tLevel;      // 수업 레벨

    @Nationalized
    @Column(name = "TSUBJECT")
    private String tSubject;    // 맡을 과목

    @Nationalized
    @Column(name = "TRESTDAY")
    private String tRestDay;    // 휴무일

    @Nationalized
    @Column(name = "TEDU")
    private String tEdu;        // 최종학력; 선생님 수정 불가

    @Nationalized
    @Column(name = "TPROFILENAME")
    private String tProfileName;    // 프로필 사진 파일명

    @Nationalized
    @Column(name = "TDESC", length = 2000)
    private String tDesc;       // 선생님 소개

//    @Nationalized
//    @Column(name = "TBOOK", length = 50)
//    private String tBook;       // 교재 커버
//
//    @Nationalized
//    @Column(name = "TBOOKCOVERNAME", length = 50)
//    private String tBookCoverName;       // 교재 커버 파일명

    @Nationalized
    @Column(name = "TGENDER", length = 10)
    private String tGender;     // 성별; 선생님 수정 불가

    @OneToMany(mappedBy = "tId") // 일정
    private Set<ScheduleEntity> scheduleEntities = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "tId") // 코멘트
//    private Set<TeacherCommentEntity> teacherComments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "tId") // 진행 학습
    private Set<ClassProgressEntity> classProgresses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "tId") // 학습 신청
    private Set<ClassRequestEntity> classRequests = new LinkedHashSet<>();

    public static TeacherInfoEntity toEntity(TeacherInfoDTO dto) {
        TeacherInfoEntity entity = new TeacherInfoEntity();

        entity.setTId(dto.getTId());
        entity.setTPass(dto.getTPass());
        entity.setTName(dto.getTName());
        entity.setTPhone(dto.getTPhone());
        entity.setTEmail(dto.getTEmail());
        entity.setTArea(dto.getTArea());
        // entity.setTStartTime(dto.getTStartTime());
        // entity.setTEndTime(dto.getTEndTime());
        entity.setTGrade(dto.getTGrade());
        entity.setTLevel(dto.getTLevel());
        entity.setTSubject(dto.getTSubject());
        entity.setTRestDay(dto.getTRestDay());
        entity.setTEdu(dto.getTEdu());
        entity.setTProfileName(dto.getTProfileName());
        entity.setTDesc(dto.getTDesc());
        // entity.setTBook(dto.getTBook());
        // entity.setTBookCoverName(dto.getTBookCoverName());
        entity.setTGender(dto.getTGender());

        return entity;
    }

}