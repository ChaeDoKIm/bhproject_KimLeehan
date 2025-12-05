package com.kimleehan.bhproject.admin.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

// TODO: 학부모/선생님 문의사항 답변 분리
@Data
@Entity
@Table(name = "FAQ_ANSWER")
public class FaqAnswerEntity {
    @Id
    @Column(name = "ANSID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동생성 수정
    private Long ansId;

    @Nationalized
    @Column(name = "ANSCONTENTS")
    private String ansContents;

    @Column(name = "ANSDATE", updatable = false)
    @CreationTimestamp
    private Instant ansDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "AID")
    private AdminInfoEntity aId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "QID")
    private FaqBoardEntity qId;

    public static FaqAnswerEntity toEntity (FaqAnswerDTO dto) {
        FaqAnswerEntity entity = new FaqAnswerEntity();

        entity.setAnsId(dto.getAnsId());
        entity.setAnsContents(dto.getAnsContents());
        entity.setAnsDate(dto.getAnsDate());

        AdminInfoEntity adminInfo = new AdminInfoEntity();
        adminInfo.setAId(dto.getAId());
        entity.setAId(adminInfo);

        FaqBoardEntity faqBoard = new FaqBoardEntity();
        faqBoard.setQId(dto.getQId());
        entity.setQId(faqBoard);
        return entity;
    }

}