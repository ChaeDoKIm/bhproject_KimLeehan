package com.kimleehan.bhproject.admin.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="TEACHER_SPEC")
public class TeacherSpecEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TSID")
    private Long tSid;

    @Column(name="TID")
    private String tId;

    @Column(name="SPECNAME")
    private String specName;

    @Column(name="SPECFILENAME")
    private String specFileName;

    @Column(name="SPECSTATUS")
    private String specStatus;

    public static TeacherSpecEntity toEntity(TeacherSpecDTO dto) {
        TeacherSpecEntity entity = new TeacherSpecEntity();

        entity.setTSid(dto.getTSid());
        entity.setTId(dto.getTId());
        entity.setSpecName(dto.getSpecName());
        entity.setSpecFileName(dto.getSpecFileName());
        entity.setSpecStatus(dto.getSpecStatus());

        return entity;
    }
}
