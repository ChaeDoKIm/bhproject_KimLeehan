package com.kimleehan.bhproject.controller;

import com.kimleehan.bhproject.vrp.controller.MainController;
import com.kimleehan.bhproject.vrp.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MainControllerTest {
    @Autowired
    private MainController mainController;

    @Autowired
    private NodeService nodeService;

//    @Test
//    public void getPoiTest() throws IOException, InterruptedException {
//        JsonResult jsonResult = mainController.getPoi(126.675113024566, 37.4388938204128);// 인천일보아카데미
//        JsonResult.Code code = jsonResult.getCode();
//        Map<String, Object> data = jsonResult.getData();
//        System.out.println("code : " + code);
//        System.out.println("전체이동거리 : " + data.get("totalDistance"));// 전체이동거리
//        System.out.println("전체이동시간 : " + data.get("totalDuration"));// 전체이동시간
//        System.out.println("전체이동경로 : " + new ObjectMapper().writeValueAsString(data.get("totalPathPointList")));// 전체이동경로
//        System.out.println("방문지목록 : " + new ObjectMapper().writeValueAsString(data.get("nodeList")));// 방문지목록
//    }
//
//    @Test
//    public void postVrpTest() throws IOException, InterruptedException {
//        KakaoApiUtil.Point center = new KakaoApiUtil.Point(126.675113024566, 37.4388938204128);// 인천일보아카데미
//        List<KakaoApiUtil.Point> pointByKeyword = KakaoApiUtil.getPointByKeyword("약국", center);
//        List<NodeEntity> nodeList = new ArrayList<>();
//        for (KakaoApiUtil.Point point : pointByKeyword) {
//            NodeEntity node = new NodeEntity();
//            node.setId(Long.valueOf(point.getId()));// 노드id
//            node.setName(point.getName());
//            node.setPhone(point.getPhone());// 전화번호
//            node.setAddress(point.getAddress());// 주소
//            node.setX(point.getX());// 경도
//            node.setY(point.getY());// 위도
//            node.setRegDt(new Date());// 등록일시
//            node.setModDt(new Date());// 수정일시
//            nodeList.add(node);
//        }
//        JsonResult jsonResult = mainController.postVrp(nodeList);
//        JsonResult.Code code = jsonResult.getCode();
//        Map<String, Object> data = jsonResult.getData();
//        System.out.println("code : " + code);
//        System.out.println("전체이동거리 : " + data.get("totalDistance"));// 전체이동거리
//        System.out.println("전체이동시간 : " + data.get("totalDuration"));// 전체이동시간
//        System.out.println("전체이동경로 : " + new ObjectMapper().writeValueAsString(data.get("totalPathPointList")));// 전체이동경로
//        System.out.println("방문지목록 : " + new ObjectMapper().writeValueAsString(data.get("nodeList")));// 방문지목록
//    }
//
//    @Test
//    public void postVrpByAddressTest() throws IOException, InterruptedException {
//        // KakaoApiUtil.Point center = new KakaoApiUtil.Point(126.675113024566, 37.4388938204128);// 인천일보아카데미
//        List<String> addresses = new ArrayList<>();
//        addresses.add("인천 미추홀구 소성로 333");
//        addresses.add("인천 미추홀구 매소홀로 578");
//        addresses.add("인천 미추홀구 한나루로 411");
//        addresses.add("인천 미추홀구 소성로 150");
//
//        List<NodeEntity> nodeList = nodeService.saveNodesByAddress(addresses);
//
//        JsonResult jsonResult = mainController.postVrp(nodeList);
//        JsonResult.Code code = jsonResult.getCode();
//        Map<String, Object> data = jsonResult.getData();
//        System.out.println("code : " + code);
//        System.out.println("전체이동거리 : " + data.get("totalDistance"));// 전체이동거리
//        System.out.println("전체이동시간 : " + data.get("totalDuration"));// 전체이동시간
//        System.out.println("전체이동경로 : " + new ObjectMapper().writeValueAsString(data.get("totalPathPointList")));// 전체이동경로
//        System.out.println("방문지목록 : " + new ObjectMapper().writeValueAsString(data.get("nodeList")));// 방문지목록
//    }

}
