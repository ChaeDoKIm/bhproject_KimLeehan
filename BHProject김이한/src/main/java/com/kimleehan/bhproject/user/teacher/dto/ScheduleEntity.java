package com.kimleehan.bhproject.user.teacher.dto;

import com.kimleehan.bhproject.user.parent.dto.ClassRequestEntity;
import com.kimleehan.bhproject.user.parent.dto.StudentInfoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SCHEDULES")
public class ScheduleEntity {
    @Id
    @Column(name = "PLANID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;        // 일정 ID

    @Column(name = "PLANSTARTTIME")
    private String planStartTime; // 일정 시작 시간

    @Column(name = "PLANENDTIME")
    private String planEndTime;   // 일정 끝나는 시간

    @Nationalized
    @Column(name = "PLANDAY", length = 50)
    private String planDay;     // 일정 요일

    @Nationalized
    @Column(name = "PLANPLACE")
    private String planPlace;   // 장소/주소

    @Nationalized
    @Column(name = "PLANSTATUS", length = 50)
    private String planStatus;  // 일정 진행상태

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CLREQID")
    private ClassRequestEntity clReqId; // 요청한 반 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TID")
    private TeacherInfoEntity tId; // 일정 맡은 선생님

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "SID")
    private StudentInfoEntity sId; // 학습 받을 학생

    public static ScheduleEntity toEntity (SchedulesDTO dto) {
        ScheduleEntity entity = new ScheduleEntity();

        entity.setPlanId(dto.getPlanId());
        entity.setPlanStartTime(dto.getPlanStartTime());
        entity.setPlanEndTime(dto.getPlanEndTime());
        entity.setPlanDay(dto.getPlanDay());
        entity.setPlanPlace(dto.getPlanPlace());
        entity.setPlanStatus(dto.getPlanStatus());

        TeacherInfoEntity teacher = new TeacherInfoEntity();
        teacher.setTId(dto.getTId());
        entity.setTId(teacher);

        StudentInfoEntity student = new StudentInfoEntity();
        student.setSId(dto.getSId());
        entity.setSId(student);

        ClassRequestEntity classReq = new ClassRequestEntity();
        classReq.setClReqId(dto.getClReqId());
        entity.setClReqId(classReq);

        return entity;
    }

}