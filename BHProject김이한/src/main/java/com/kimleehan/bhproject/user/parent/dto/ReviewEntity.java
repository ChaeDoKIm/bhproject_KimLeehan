package com.kimleehan.bhproject.user.parent.dto;

import com.kimleehan.bhproject.user.teacher.dto.TeacherInfoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "REVIEW")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVID", nullable = false)
    private Long revId;         // 후기 ID

    @Column(name = "REVDATE")
    private Instant revDate;    // 후기 작성/등록일

    @Column(name = "REVRATE")
    private Long revRate;       // 후기에 남긴 별점

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "SID")
    private StudentInfoEntity sId;   // 작성자 (학생 ID)

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TID")
    private TeacherInfoEntity tId;  // 후기 받은 선생님

    public static ReviewEntity toEntity(ReviewDTO dto) {
        ReviewEntity entity = new ReviewEntity();
        entity.setRevId(dto.getRevId());
        entity.setRevDate(dto.getRevDate());
        entity.setRevRate(dto.getRevRate());

        TeacherInfoEntity teacher = new TeacherInfoEntity();
        teacher.setTId(dto.getTId());
        entity.setTId(teacher);


        StudentInfoEntity student = new StudentInfoEntity();
        student.setSId(dto.getSId());
        entity.setSId(student);

        return entity;
    }

}