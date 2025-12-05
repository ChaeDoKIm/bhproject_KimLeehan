package com.kimleehan.bhproject.user.teacher.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="TEACHER_COMMENT")
public class TeacherCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CID")
    private Long cId; // 코멘트 id

    @Column(name="CDATE", updatable = false)
    @CreationTimestamp
    private LocalDateTime cDate; // 작성일

    @Column(name = "CPROGRESS", length = 100)
    @Nationalized
    private String cProgress;   // 학습 진도 (몇주차)

    @Column(name = "CPAGE", length = 50)
    private String cPage;       // 나간 진도 (페이지수)

    @Column(name = "CCLASSCONTENTS", length = 500)
    private String cClassContents; // 수업 내용 (예: 현재완료형, 과거분사, 단어시험)

    @Column(name = "CCONTENTS", length = 5000)
    @Nationalized
    private String cContents;   // 학부모님에게 전할 말

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CLPROGID")  // 코멘트 달 진행학습 id
    private ClassProgressEntity clProgId;


    public static TeacherCommentEntity toEntity(TeacherCommentDTO dto) {
        TeacherCommentEntity entity = new TeacherCommentEntity();

        entity.setCId(dto.getCId());
        entity.setCDate(dto.getCDate());
        entity.setCProgress(dto.getCProgress());
        entity.setCPage(dto.getCPage());
        entity.setCContents(dto.getCContents());
        entity.setCClassContents(dto.getCClassContents());

        ClassProgressEntity classProgress = new ClassProgressEntity();
        classProgress.setClProgId(dto.getClProgId());
        entity.setClProgId(classProgress);

        return entity;
    }


}
