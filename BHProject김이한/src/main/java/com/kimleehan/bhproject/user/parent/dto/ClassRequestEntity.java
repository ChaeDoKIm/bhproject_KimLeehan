package com.kimleehan.bhproject.user.parent.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kimleehan.bhproject.admin.dto.ClassPaymentEntity;
import com.kimleehan.bhproject.user.teacher.dto.ScheduleEntity;
import com.kimleehan.bhproject.user.teacher.dto.TeacherInfoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CLASS_REQUEST")
@JsonIgnoreProperties(ignoreUnknown= true)
public class ClassRequestEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "CLREQID", nullable = false)
    @JsonProperty("clReqId")
    private Long clReqId;   // 학습 신청 아이디

    @Column(name = "CLREQDATE", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant clReqDate;  // 신청일

    @Nationalized
    @Column(name = "CLREQPLACE")
    private String clReqPlace;  // 학습 받기 원하는 장소/주소

    @Nationalized
    @Column(name = "CLREQDAY", length = 50)
    private String clReqDay;    // 희망요일

    @Nationalized
    @Column(name = "CLREQLEVEL", length = 50)
    private String clReqLevel;  // 학습 레벨 (기초, 중/고, 심화)

    @Nationalized
    @Column(name = "CLREQSUBJECT", length = 50)
    private String clReqSubject;  // 학습할 과목

    @Nationalized
    @Column(name = "CLREQSTATUS", length = 50)
    private String clReqStatus;  // 신청서 확인 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "SID")
    private StudentInfoEntity sId;  // 학습 받을 학생 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PID")
    private ParentInfoEntity pId;   // 신청한 학부모 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TID")
    private TeacherInfoEntity tId;  // 희망 선생님

    @OneToMany(mappedBy = "clReqId")
    private Set<ScheduleEntity> schedules = new LinkedHashSet<>();

    @OneToMany(mappedBy = "clReqId")
    private Set<ClassPaymentEntity> classPayments = new LinkedHashSet<>();

    public static ClassRequestEntity toEntity(ClassRequestDTO dto) {
        ClassRequestEntity entity = new ClassRequestEntity();

        entity.setClReqId(dto.getClReqId());
        entity.setClReqDate(dto.getClReqDate());
        entity.setClReqPlace(dto.getClReqPlace());
        entity.setClReqDay(dto.getClReqDay());
        entity.setClReqLevel(dto.getClReqLevel());
        entity.setClReqSubject(dto.getClReqSubject());
        entity.setClReqStatus(dto.getClReqStatus());

        StudentInfoEntity student = new StudentInfoEntity();
        student.setSId(dto.getSId());
        entity.setSId(student);

        ParentInfoEntity parent = new ParentInfoEntity();
        parent.setPId(dto.getPId());
        entity.setPId(parent);

        TeacherInfoEntity teacher = new TeacherInfoEntity();
        teacher.setTId(dto.getTId());
        entity.setTId(teacher);

        return entity;
    }

    public ClassRequestEntity(int clReqId) {
        this.clReqId = (long) clReqId;
    }

}