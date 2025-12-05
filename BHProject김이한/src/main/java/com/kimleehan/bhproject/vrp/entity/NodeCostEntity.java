package com.kimleehan.bhproject.vrp.entity;


import com.kimleehan.bhproject.vrp.dto.NodeCostDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity
@Table(name="node_cost")
public class NodeCostEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//노드비용id

    @Column(name="start_node_id")
    private Long startNodeId;//시작노드id

    @Column(name="end_node_id")
    private Long endNodeId;//종료노드id

    @Column(name="distance_meter")
    private Long distanceMeter;//이동거리(미터)

    @Column(name="duration_second")
    private Long durationSecond;//이동시간(초)

    @Column(name="toll_fare")
    private Integer tollFare;//통행 요금(톨게이트)

    @Column(name="taxi_fare")
    private Integer taxiFare;//택시 요금(지자체별, 심야, 시경계, 복합, 콜비 감안)

    @Column(name="fuel_price")
    private Integer fuelPrice;//해당 시점의 전국 평균 유류비와 연비를 감안한 유류비

    @Column(name="path_json", length = 60000)
    private String pathJson;//이동경로json [[x,y],[x,y]]

    @Column(name="reg_dt", updatable = false)
    @CreationTimestamp
    private Date regDt;//등록일시

    @Column(name="mod_dt", insertable = false)
    @CreationTimestamp
    private Date modDt;//수정일시

    public static NodeCostEntity toEntity(NodeCostDTO dto) {
        NodeCostEntity entity = new NodeCostEntity();

        entity.setId(dto.getId());
        entity.setStartNodeId(dto.getStartNodeId());
        entity.setEndNodeId(dto.getEndNodeId());
        entity.setDistanceMeter(dto.getDistanceMeter());
        entity.setDurationSecond(dto.getDurationSecond());
        entity.setTollFare(dto.getTollFare());
        entity.setTaxiFare(dto.getTaxiFare());
        entity.setFuelPrice(dto.getFuelPrice());
        entity.setPathJson(dto.getPathJson());

        return entity;
    }

}
