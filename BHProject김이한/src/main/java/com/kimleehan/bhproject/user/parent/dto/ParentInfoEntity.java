package com.kimleehan.bhproject.user.parent.dto;

import com.kimleehan.bhproject.admin.dto.ClassPaymentEntity;
import com.kimleehan.bhproject.admin.dto.SignupRequestEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "PARENT_INFO")
public class ParentInfoEntity {
    @Id
    @Nationalized
    @Column(name = "PID", nullable = false, length = 50)
    private String pId;     // 학부모 ID

    @Nationalized
    @Column(name = "PPASS", nullable = false)
    private String pPass;   // 학부모 비밀번호

    @Nationalized
    @Column(name = "PNAME", nullable = false, length = 100)
    private String pName;   // 학부모 성함

    @Nationalized
    @Column(name = "PPHONE", length = 20)
    private String pPhone;  // 연락처

    @Nationalized
    @Column(name = "PADDRESS")
    private String pAddress;  // 주소

    @Nationalized
    @Column(name = "PEMAIL", length = 100)
    private String pEmail;      // 이메일

    @OneToMany(mappedBy = "pId")
    private Set<StudentInfoEntity> studentInfos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pId")
    private Set<ClassRequestEntity> classRequests = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pId")
    private Set<ClassPaymentEntity> classPayments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pId")
    private Set<SignupRequestEntity> signupRequests = new LinkedHashSet<>();

    public static ParentInfoEntity toEntity(ParentInfoDTO dto) {
        ParentInfoEntity entity = new ParentInfoEntity();

        entity.setPId(dto.getPId());
        entity.setPPass(dto.getPPass());
        entity.setPName(dto.getPName());
        entity.setPPhone(dto.getPPhone());
        entity.setPAddress(dto.getPAddress());
        entity.setPEmail(dto.getPEmail());

        return entity;
    }


}