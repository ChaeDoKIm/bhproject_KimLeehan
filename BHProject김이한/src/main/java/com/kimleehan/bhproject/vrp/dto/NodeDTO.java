package com.kimleehan.bhproject.vrp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NodeDTO {

    private Long id;//노드id
    private String name;
    private String address;
    private String phone;
    private Double x;//경도
    private Double y;//위도
    private Date regDt;//등록일시
    private Date modDt;//수정일시

}
