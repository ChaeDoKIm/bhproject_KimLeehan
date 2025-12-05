package com.kimleehan.bhproject.vrp.service;


import com.kimleehan.bhproject.vrp.dao.NodeRepository;
import com.kimleehan.bhproject.vrp.entity.NodeEntity;
import com.kimleehan.bhproject.vrp.util.KakaoApiUtil;
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
public class NodeService {

    private final NodeRepository noderepo;

    public void add(NodeEntity node) {
        noderepo.save(node);
    }

    public NodeEntity getOne(Long Long) {
        return noderepo.findById(Long).orElse(null);
    }

    public List<NodeEntity> saveNodes(double x, double y) {
        KakaoApiUtil.Point center = new KakaoApiUtil.Point(x, y);// 중심좌표
        List<KakaoApiUtil.Point> pointList = null;
        try {
            pointList = KakaoApiUtil.getPointByKeyword("약국", center);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<NodeEntity> nodeList = new ArrayList<>();
        for (KakaoApiUtil.Point point : pointList) {
            Optional<NodeEntity> node = noderepo.findById(Long.valueOf(point.getId()));
            NodeEntity newNode = new NodeEntity();
            if (node.isEmpty()) {

                newNode.setId(Long.valueOf(point.getId()));// 노드id
                newNode.setName(point.getName());
                newNode.setPhone(point.getPhone());// 전화번호
                newNode.setAddress(point.getAddress());// 주소
                newNode.setX(point.getX());// 경도
                newNode.setY(point.getY());// 위도
                newNode.setRegDt(new Date());// 등록일시
                newNode.setModDt(new Date());// 수정일시

                noderepo.save(newNode);
                nodeList.add(newNode);
            } else {
                nodeList.add(node.get());
            }
        }
        return nodeList;
    }

    // 중요 여기 주석 풀기
    /**
     * 여러 clReqPlace(주소)를 처리하여 NodeEntity 리스트 반환
     */
    public List<NodeEntity> saveNodesByClReqPlace(List<String> clReqPlaces) {
        List<NodeEntity> nodeList = new ArrayList<>();

        for (String clReqPlace : clReqPlaces) {
            // 각 주소를 처리하여 NodeEntity 생성
            NodeEntity node = saveNodeByClReqPlace(clReqPlace);
            nodeList.add(node);
        }

        return nodeList; // 결과 리스트 반환
    }

    // 찐코드 요기 주석풀기
    public NodeEntity saveNodeByClReqPlace(String clReqPlace) {
        KakaoApiUtil.Point point;
        try {
            // Kakao API를 사용하여 clReqPlace를 좌표로 변환
            point = KakaoApiUtil.getPointByAddress(clReqPlace);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("주소 변환 실패: " + clReqPlace, e);
        }

        // 좌표 값 검증
        if (point.getX() == null || point.getY() == null) {
            throw new IllegalArgumentException("Kakao API 응답에서 좌표 값이 누락되었습니다.");
        }

        // 데이터베이스에서 동일한 좌표 확인
        Optional<NodeEntity> existingNode = noderepo.findByXAndY(point.getX(), point.getY());
        if (existingNode.isPresent()) {
            return existingNode.get(); // 기존 데이터 반환
        }

        // 새로운 NodeEntity 생성 및 저장
        NodeEntity newNode = new NodeEntity();

        // id 생성: API가 ID를 제공하지 않으므로 UUID 또는 데이터베이스 ID 사용
        newNode.setId(Optional.ofNullable(point.getId())
                .map(Long::valueOf)
                .orElse(null)); // ID가 없을 경우 null 처리 또는 자동 생성
        newNode.setName(point.getName()); // 장소 이름
        newNode.setAddress(clReqPlace); // 입력 주소
        newNode.setX(point.getX()); // 경도
        newNode.setY(point.getY()); // 위도
        newNode.setRegDt(new Date()); // 등록일
        newNode.setModDt(new Date()); // 수정일

        noderepo.save(newNode); // 데이터베이스 저장
        return newNode;
    }

}



