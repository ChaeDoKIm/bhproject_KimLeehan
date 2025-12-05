package com.kimleehan.bhproject.vrp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimleehan.bhproject.vrp.dao.NodeCostRepository;
import com.kimleehan.bhproject.vrp.dto.NodeCostDTO;
import com.kimleehan.bhproject.vrp.entity.NodeCostEntity;
import com.kimleehan.bhproject.vrp.entity.NodeEntity;
import com.kimleehan.bhproject.vrp.util.JsonResult;
import com.kimleehan.bhproject.vrp.util.KakaoApiUtil;
import com.kimleehan.bhproject.vrp.util.kakao.KakaoDirections;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NodeCostService {

    private final NodeCostRepository nodeCostrepo;

    /*
     * 주요 동작 흐름
     * 1. add 메서드
     *  -> 입력된 노드 리스트를 최적화 수행( optimizeRoute 호출)
     *  -> 최적화된 순서대로 이동 거리 및 시간을 계산
     *  -> 결과 데이터를 JsonResult로 반환
     *
     * 2. optimizeRoute 메서드
     *    -> 출발지를 첫번째로 고정
     *    -> 현재 노드에서 가장 가까운 노드를 반복적으로 찾아 최적화된 순서를 만듦
     *    -> 순서 최적화를 위해 NodeCostEntity에서 거리 정보를 활용
     * */


    public JsonResult add(List<NodeEntity> nodeList) {
        JsonResult jsonResult = new JsonResult(); // 결과 데이터를 저장할 JsonResult 객체 생성

        // 1. 최적화 수행
        List<NodeEntity> optimizedNodeList = optimizeRoute(nodeList);
        // 전달된 노드 리스트를 최적화 -> 최적 경로 순서로 정렬된 리스트 반환


        long totalDistance = 0L;   // 총 이동 거리 초기화 
        long totalDuration = 0L;   // 총 이동 시간 초기화
        List<KakaoApiUtil.Point> totalPathPointList = new ArrayList<>();
        // 총 경로 데이터를 저장할 리스트 초기화


        // 2. 최적화된 순서대로 거리 및 시간 계산
        for (int i = 1; i < optimizedNodeList.size(); i++) {
            // 최적화된 노드 리스트를 순회 ( 1부터 시작 -> 이전 노드와 다음 노드 비교)
            NodeEntity prev = optimizedNodeList.get(i - 1); // 이전 노드 
            NodeEntity next = optimizedNodeList.get(i);     // 현재 노드

            NodeCostEntity nodeCost = getNodeCost(prev, next);
            // 이전 노드와 현재 노드 간의 거리 및 시간 정보를 가져옴

            totalDistance += nodeCost.getDistanceMeter();  // 총 이동 거리에 추가
            totalDuration += nodeCost.getDurationSecond(); // 총 이동 시간에 추가

            try {
                totalPathPointList.addAll(new ObjectMapper().readValue(
                        nodeCost.getPathJson(), new TypeReference<List<KakaoApiUtil.Point>>() {}
                ));

                //JSON으로 저장된 경로 데이터를 읽어와 총 경로 리스트에 추가
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);  // JSON 처리 중 오류 발생 시 예외 던짐!
            }
        }

        // 3. 결과 반환
        jsonResult.addData("totalDistance", totalDistance);           // 최적화된 총 거리 반환
        jsonResult.addData("totalDuration", totalDuration);           // 최적화된 총 시간 반환
        jsonResult.addData("totalPathPointList", totalPathPointList); // 최적화된 경로 반환
        jsonResult.addData("nodeList", optimizedNodeList);            // 최적화된 순서 반환

        return jsonResult;  // 최종 결과 반환
    }


    private List<NodeEntity> optimizeRoute(List<NodeEntity> nodeList) {
        List<NodeEntity> optimizedList = new ArrayList<>();
        // 최적화된 순서를 저장할 리스트 초기화
        List<NodeEntity> remainingNodes = new ArrayList<>(nodeList);
        // 아직 방문하지 않은 노드 리스트 복사

        System.out.println("노드리스트 확인: " + nodeList);

        NodeEntity currentNode = remainingNodes.remove(0); // 출발점 설정
        // 첫 번쨰 노드를 출발점으로 설정하고, 남은 노드 리스트에서 제거
        optimizedList.add(currentNode);
        // 출발점을 최적화된 리스트에 추가

        while (!remainingNodes.isEmpty()) {
            // 남은 노드 없을 때까지 반복!
            NodeEntity closestNode = null;      // 현재 노드에서 가장 가까운 노드를 저장할 변수
            long minDistance = Long.MAX_VALUE;  // 최소 거리를 저장할 변수 초기화

            for (NodeEntity node : remainingNodes) {
                // 남은 노드를 순회하며 가장 가까운 노드를 찾음!
                NodeCostEntity cost = getNodeCost(currentNode, node);
                // 현재 노드와 비교 노드 간의 거리 정보 가져오기!
                if (cost != null && cost.getDistanceMeter() < minDistance) {
                    // 유효한 거리 정보가 있고 최소 거리보다 작으면 업데이트
                    minDistance = cost.getDistanceMeter();
                    closestNode = node; // 가장 가까운 노드를 업데이트
                }
            }

            if (closestNode != null) {
                // 가장 가까운 노드가 있으면
                optimizedList.add(closestNode);     // 최적화된 리스트에 추가
                remainingNodes.remove(closestNode); // 남은 노드 리스트에서 제거
                currentNode = closestNode;          // 현재 노드를 업데이트
            }
            System.out.println("남은 노드 확인: " + remainingNodes);
        }

        System.out.println(optimizedList);

        return optimizedList; // 최적화된 순서 반환
    }



    public NodeCostEntity getNodeCost(NodeEntity prev, NodeEntity next) {

        NodeCostDTO nodeCostDTO = new NodeCostDTO();

        nodeCostDTO.setStartNodeId(prev.getId());
        nodeCostDTO.setEndNodeId(next.getId());

        Optional<NodeCostEntity> nodeCostEntity = nodeCostrepo.findByStartNodeIdAndEndNodeId(nodeCostDTO.getStartNodeId(), nodeCostDTO.getEndNodeId());

        NodeCostEntity newNodeCost = new NodeCostEntity();

        if (nodeCostEntity.isEmpty()) {
            KakaoDirections kakaoDirections;
            try {
                kakaoDirections = KakaoApiUtil.getKakaoDirections(new KakaoApiUtil.Point(prev.getX(), prev.getY()),
                        new KakaoApiUtil.Point(next.getX(), next.getY()));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            List<KakaoDirections.Route> routes = kakaoDirections.getRoutes();
            KakaoDirections.Route route = routes.get(0);
            List<KakaoApiUtil.Point> pathPointList = new ArrayList<>();
            List<KakaoDirections.Route.Section> sections = route.getSections();

            if (sections != null) {
                // {"trans_id":"018e3d7f7526771d9332cb717909be8f","routes":[{"result_code":104,"result_msg":"출발지와
                // 도착지가 5 m 이내로 설정된 경우 경로를 탐색할 수 없음"}]}

                List<KakaoDirections.Route.Section.Road> roads = sections.get(0).getRoads();
                for (KakaoDirections.Route.Section.Road road : roads) {
                    List<Double> vertexes = road.getVertexes();
                    for (int q = 0; q < vertexes.size(); q++) {
                        pathPointList.add(new KakaoApiUtil.Point(vertexes.get(q), vertexes.get(++q)));
                    }
                }

                Date regDt = new Date();
                KakaoDirections.Route.Summary summary = route.getSummary();
                Integer distance = summary.getDistance();
                Integer duration = summary.getDuration();
                KakaoDirections.Route.Summary.Fare fare = summary.getFare();
                Integer taxi = fare.getTaxi();
                Integer toll = fare.getToll();

                //NodeCostEntity nodeCostFindId = nodeCostrepo.findFirstByOrderByIdDesc();
                //Long nodeCostId = nodeCostFindId.getId();
                //newNodeCost.setId(nodeCostId + 1);// id
                newNodeCost.setStartNodeId(prev.getId());// 시작노드id
                newNodeCost.setEndNodeId(next.getId());// 종료노드id
                newNodeCost.setDistanceMeter(distance.longValue());// 이동거리(미터)
                newNodeCost.setDurationSecond(duration.longValue());// 이동시간(초)
                newNodeCost.setTollFare(toll);// 통행 요금(톨게이트)
                newNodeCost.setTaxiFare(taxi);// 택시 요금(지자체별, 심야, 시경계, 복합, 콜비 감안)
                try {
                    newNodeCost.setPathJson(new ObjectMapper().writeValueAsString(pathPointList));// 이동경로json [[x,y],[x,y]]
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                newNodeCost.setRegDt(regDt);// 등록일시
                newNodeCost.setModDt(new Date());// 수정일시
                nodeCostrepo.save(newNodeCost);

            }
        }
        return nodeCostEntity.orElse(newNodeCost);
    }



}
