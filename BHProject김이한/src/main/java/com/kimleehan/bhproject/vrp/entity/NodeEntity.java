package com.kimleehan.bhproject.vrp.entity;


import com.kimleehan.bhproject.vrp.dto.NodeDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity
@Table(name = "node")
public class NodeEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//노드id

    @Column(name="name")
    private String name;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    private String phone;

    @Column(name="x")
    private Double x;//경도

    @Column(name="y")
    private Double y;//위도

    @Column(name="reg_dt", updatable = false)
    @CreationTimestamp
    private Date regDt;//등록일시

    @Column(name="mod_dt", insertable = false)
    @CreationTimestamp
    private Date modDt;//수정일시

    public static NodeEntity toEntity(NodeDTO dto) {
        NodeEntity entity = new NodeEntity();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setX(dto.getX());
        entity.setY(dto.getY());

        return entity;
    }
}
