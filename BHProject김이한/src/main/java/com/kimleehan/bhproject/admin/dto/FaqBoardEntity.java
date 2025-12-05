package com.kimleehan.bhproject.admin.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
// TODO: 선생님 문의/학부모 문의 나누기
@Data
@Entity
@Table(name = "FAQ_BOARD")
public class FaqBoardEntity {

    @Id
    @Column(name = "QID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동생성 수정
    private Long qId;

    @Nationalized
    @Column(name = "QWRITER")
    private String qWriter;

    @Nationalized
    @Column(name = "QPASS")
    private String qPass;

    @Nationalized
    @Column(name = "QTITLE")
    private String qTitle;

    @Column(name = "QCATEGORY")
    private String qCategory;

    @Nationalized
    @Column(name = "QCONTENTS", length = 2000)
    private String qContents;

    @Column(name = "QDATE", updatable = false)
    @CreationTimestamp
    private Instant qDate;

    @Nationalized
    @Column(name="QSTATUS")
    private String qStatus;

    @OneToMany(mappedBy = "qId")
    private Set<FaqAnswerEntity> faqAnswerEntities = new LinkedHashSet<>();

    public static FaqBoardEntity toEntity(FaqBoardDTO dto) {
        FaqBoardEntity entity = new FaqBoardEntity();

        entity.setQId(dto.getQId());
        entity.setQTitle(dto.getQTitle());
        entity.setQWriter(dto.getQWriter());
        entity.setQPass(dto.getQPass());
        entity.setQCategory(dto.getQCategory());
        entity.setQContents(dto.getQContents());
        entity.setQDate(dto.getQDate());
        entity.setQStatus(dto.getQStatus());

        return entity;
    }


}