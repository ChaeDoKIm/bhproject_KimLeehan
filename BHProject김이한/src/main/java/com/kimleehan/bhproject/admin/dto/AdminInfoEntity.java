package com.kimleehan.bhproject.admin.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "ADMIN_INFO")
public class AdminInfoEntity {
    @Id
    @Nationalized
    @Column(name = "AID", nullable = false, length = 50)
    private String aId;     // 관리자 아이디

    @Nationalized
    @Column(name = "APASS", nullable = false)
    private String aPass;   // 관리자 비번

    @OneToMany(mappedBy = "aId")
    private Set<FaqAnswerEntity> faqAnswerEntities = new LinkedHashSet<>();

    public static AdminInfoEntity toEntity(AdminInfoDTO dto) {
        AdminInfoEntity entity = new AdminInfoEntity();

        entity.setAId(dto.getAId());
        entity.setAPass(dto.getAPass());

        return entity;
    }

}