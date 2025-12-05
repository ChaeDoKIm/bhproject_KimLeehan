package com.kimleehan.bhproject.user.teacher.dto;

import com.kimleehan.bhproject.user.parent.dto.StudentInfoEntity;
import jakarta.persistence.*;

import lombok.Data;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "CLASS_PROGRESS")
public class ClassProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLPROGID")
    private Long clProgId;              // 진행된 학습 id

    @Nationalized
    @Column(name = "CLPROGSUBJECT")
    private String clProgSubject;       // 진행 과목

    @Nationalized
    @Column(name = "CLPROGBOOK")
    private String clProgBook;          // 사용하는 교재

    @Nationalized
    @Column(name = "CLPROGLEVEL")
    private String clProgLevel;         // 학습 레벨

    @Column(name = "CLPROGSTARTDATE")
    private LocalDate clProgStartDate;  // 학습 시작일

    @Column(name = "CLPROGENDDATE")
    private LocalDate clProgEndDate;    // 학습 종료일

    @Column(name = "CLPROGPERCENT")
    private Double clProgPercent;       // 학습 진행률 %

    @Nationalized
    @Column(name = "CLPROGSTATUS")
    private String clProgStatus;        // 학습 진행상태 (예정, 진행중, 종료, 중단/중도포기)

    @Column(name = "CLPROGDAY")
    private String clProgDay;           // 학습 요일

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "SID")
    private StudentInfoEntity sId;  // 학습 듣는 학생 id

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TID")
    private TeacherInfoEntity tId;  // 학습 진행하는 선생님 id

    public static ClassProgressEntity toEntity (ClassProgressDTO dto) {
        ClassProgressEntity entity = new ClassProgressEntity();

        entity.setClProgId(dto.getClProgId());
        entity.setClProgSubject(dto.getClProgSubject());
        entity.setClProgBook(dto.getClProgBook());
        entity.setClProgStartDate(dto.getClProgStartDate());
        entity.setClProgEndDate(dto.getClProgEndDate());
        entity.setClProgPercent(dto.getClProgPercent());
        entity.setClProgStatus(dto.getClProgStatus());
        entity.setClProgDay(dto.getClProgDay());

        StudentInfoEntity student = new StudentInfoEntity();
        student.setSId(dto.getSId());
        entity.setSId(student);

        TeacherInfoEntity teacher = new TeacherInfoEntity();
        teacher.setTId(dto.getTId());
        entity.setTId(teacher);

        return entity;
    }

}
