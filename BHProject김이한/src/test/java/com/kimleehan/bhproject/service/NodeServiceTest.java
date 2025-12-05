package com.kimleehan.bhproject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimleehan.bhproject.vrp.entity.NodeEntity;
import com.kimleehan.bhproject.vrp.service.NodeService;
import com.kimleehan.bhproject.vrp.util.KakaoApiUtil;
import com.kimleehan.bhproject.vrp.util.kakao.KakaoDirections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class NodeServiceTest {
  @Autowired
  private NodeService nodeService;

  @Test
  public void addTest() throws IOException, InterruptedException {
    KakaoApiUtil.Point center = new KakaoApiUtil.Point(126.675113024566, 37.4388938204128);// 인천일보아카데미
    List<KakaoApiUtil.Point> pointByKeyword = KakaoApiUtil.getPointByKeyword("약국", center);
    for (KakaoApiUtil.Point point : pointByKeyword) {
      NodeEntity node = new NodeEntity();
      node.setId(Long.valueOf(point.getId()));// 노드id
      node.setName(point.getName());
      node.setPhone(point.getPhone());// 전화번호
      node.setAddress(point.getAddress());// 주소
      node.setX(point.getX());// 경도
      node.setY(point.getY());// 위도
      node.setRegDt(new Date());// 등록일시
      node.setModDt(new Date());// 수정일시
      nodeService.add(node);
    }

  }

//  @Test
//  public void addByAddressListTest() throws IOException, InterruptedException {
//    List<String> addresses = new ArrayList<>();
//    addresses.add("인천 미추홀구 소성로 333");
//    addresses.add("인천 미추홀구 매소홀로 578");
//    addresses.add("인천 미추홀구 한나루로 411");
//    addresses.add("인천 미추홀구 소성로 150");
//
//    nodeService.saveNodesByAddress(addresses);
//  }

  @Test
  public void getKakaoDirectionsTest() throws IOException, InterruptedException {
    KakaoApiUtil.Point from = new KakaoApiUtil.Point(126.675113024566, 37.4388938204128);// 인천일보아카데미
    System.out.println("인천일보아카데미) x:" + from.getX() + ",y:" + from.getY());
    KakaoApiUtil.Point to = KakaoApiUtil.getPointByAddress("와우산로 23길 20 패스트파이브 5층");
    System.out.println("브이유에스) x:" + to.getX() + ",y:" + to.getY());
    System.out.println("출발!!");
    KakaoDirections kakaoDirections = KakaoApiUtil.getKakaoDirections(from, to);
    List<KakaoDirections.Route> routes = kakaoDirections.getRoutes();
    KakaoDirections.Route route = routes.get(0);
    List<KakaoApiUtil.Point> pointList = new ArrayList<KakaoApiUtil.Point>();
    List<KakaoDirections.Route.Section.Road> roads = route.getSections().get(0).getRoads();
    for (KakaoDirections.Route.Section.Road road : roads) {
      List<Double> vertexes = road.getVertexes();
      for (int i = 0; i < vertexes.size(); i++) {
        pointList.add(new KakaoApiUtil.Point(vertexes.get(i), vertexes.get(++i)));
      }
    }
    KakaoDirections.Route.Summary summary = route.getSummary();
    Integer distance = summary.getDistance();
    Integer duration = summary.getDuration();
    KakaoDirections.Route.Summary.Fare fare = summary.getFare();
    Integer taxi = fare.getTaxi();
    Integer toll = fare.getToll();
    System.out.println("이동거리:" + distance);
    System.out.println("이동시간:" + duration);
    System.out.println("택시비:" + taxi);
    System.out.println("톨비:" + toll);
    System.out.println("경로:" + new ObjectMapper().writeValueAsString(pointList));
  }

}
