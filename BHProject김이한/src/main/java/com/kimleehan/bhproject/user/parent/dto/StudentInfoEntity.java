package com.kimleehan.bhproject.user.parent.dto;

import com.kimleehan.bhproject.user.teacher.dto.ClassProgressEntity;
import com.kimleehan.bhproject.user.teacher.dto.ScheduleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "STUDENT_INFO")
public class StudentInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SID", nullable = false, length = 50)
    private Long sId;         // 학생 아이디 (등록 메소드에 아이디 생성 로직)

    @Nationalized
    @Column(name = "SNAME", nullable = false, length = 100)
    private String sName;       // 학생 이름

    @Nationalized
    @Column(name = "SGRADE", length = 50)
    private String sGrade;      // 학년

    @Nationalized
    @Column(name = "SGENDER", length = 10)
    private String sGender;     // 성별

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PID")   // 학부모 id
    private ParentInfoEntity pId;

    @OneToMany(mappedBy = "sId")
    private Set<ClassRequestEntity> classRequests = new LinkedHashSet<>();

    @OneToMany(mappedBy = "sId")
    private Set<ScheduleEntity> scheduleEntities = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "sId")
//    private Set<TeacherCommentEntity> teacherComments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "sId")
    private Set<ClassProgressEntity> classProgresses = new LinkedHashSet<>();

    public static StudentInfoEntity toEntity(StudentInfoDTO dto) {
        StudentInfoEntity entity = new StudentInfoEntity();

        entity.setSId(dto.getSId());
        entity.setSName(dto.getSName());
        entity.setSGrade(dto.getSGrade());
        entity.setSGender(dto.getSGender());

        ParentInfoEntity parent = new ParentInfoEntity();
        parent.setPId(dto.getPId());
        entity.setPId(parent);

        return entity;
    }

}