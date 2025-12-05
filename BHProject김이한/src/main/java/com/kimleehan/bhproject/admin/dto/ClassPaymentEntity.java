package com.kimleehan.bhproject.admin.dto;

import com.kimleehan.bhproject.user.parent.dto.ClassRequestEntity;
import com.kimleehan.bhproject.user.parent.dto.ParentInfoEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Data
@Entity
@Table(name = "CLASS_PAYMENT")
public class ClassPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLPAYID", nullable = false)
    private Long clPayId;

    @Column(name = "CLPAYPRICE", precision = 10, scale = 2)
    private int clPayPrice;

    @Nationalized
    @Column(name = "CLSTATUS", length = 50)
    private String clStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CLREQID")
    private ClassRequestEntity clReqId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PID")
    private ParentInfoEntity pId;

    public static ClassPaymentEntity toEntity(ClassPaymentDTO dto) {
        ClassPaymentEntity entity = new ClassPaymentEntity();
        entity.setClPayId(dto.getClPayId());
        entity.setClPayPrice(dto.getClPayPrice());
        entity.setClStatus(dto.getClStatus());

        ClassRequestEntity clrEntity = new ClassRequestEntity();
        clrEntity.setClReqId(dto.getClReqId());
        entity.setClReqId(clrEntity);

        ParentInfoEntity parent = new ParentInfoEntity();
        parent.setPId(dto.getPId());
        entity.setPId(parent);

        return entity;
    }
}