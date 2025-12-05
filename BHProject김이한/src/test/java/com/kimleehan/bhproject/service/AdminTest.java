package com.kimleehan.bhproject.service;

import com.kimleehan.bhproject.admin.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class AdminTest {

    @Autowired
    private ScheduleService schsvc;

    @Test
    public void checkEndDateCalculation()  {
        LocalDate isCorrectDate = schsvc.calculateEndDate(LocalDate.now(), "금요일");
        System.out.println("최종 날짜 확인 : " + isCorrectDate);
    }

//
//    @Test // 주소목록으로 노드와 비용 확인 테스트
//    public void getNodeListTest() throws IOException, InterruptedException {
//        // 검색할 선생님 id
//        String tId = "teachertest1";
//
//        // 주소목록 불러오기
//        List<String> addresses = classService.getCRAddressListTest(tId);
//        // 주소목록 확인
//        System.out.println(addresses);
//        // 노드로 바꾸기 및 저장
//        List<NodeEntity> nodeList = nodeService.saveNodesByAddress(addresses);
//        // vrp 컨트롤러에 있는 메소드 돌리기
//        JsonResult jsonResult = vrpController.postVrp(nodeList);
//        JsonResult.Code code = jsonResult.getCode();
//        Map<String, Object> data = jsonResult.getData();
//        // 확인
//        System.out.println("code : " + code);
//        System.out.println("전체이동거리 : " + data.get("totalDistance"));// 전체이동거리
//        System.out.println("전체이동시간 : " + data.get("totalDuration"));// 전체이동시간
//        System.out.println("전체이동경로 : " + new ObjectMapper().writeValueAsString(data.get("totalPathPointList")));// 전체이동경로
//        System.out.println("방문지목록 : " + new ObjectMapper().writeValueAsString(data.get("nodeList")));// 방문지목록
//    }

}
