package com.kimleehan.bhproject.vrp.vrp;

import lombok.Data;

@Data
public class VrpVehicleRoute {

  private int routeNo; // 경로 번호
  private String vehicleId; // 차량 ID
  private String activityName; // 활동 이름
  private String locationId; // 위치 ID
  private String jobId; // 업무 ID
  private Long arrivalTime; // 도착 시간
  private Long endTime; // 업무 종료 시간
  private Long costs; // 비용
  private Long totalDistance; // 누적 이동 거리
  private Long totalTime; // 누적 시간
  private Long totalVisitCount; // 누적 방문지 개수

}
