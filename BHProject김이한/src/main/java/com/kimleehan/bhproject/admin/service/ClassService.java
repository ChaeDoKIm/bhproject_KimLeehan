package com.kimleehan.bhproject.admin.service;

import com.kimleehan.bhproject.user.parent.dao.ClassRequestRepository;
import com.kimleehan.bhproject.user.parent.dto.ClassRequestDTO;
import com.kimleehan.bhproject.user.parent.dto.ClassRequestEntity;
import com.kimleehan.bhproject.user.teacher.dao.ScheduleRepository;
import com.kimleehan.bhproject.vrp.service.NodeCostService;
import com.kimleehan.bhproject.vrp.service.NodeService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassService {

    // 학습/과외 신청서
    private final ClassRequestRepository clrqrepo;

    // VRP 서비스
    private final NodeService nodesvc;
    private final NodeCostService nodeCostsvc;

    // 학습/과외 일정
    private final ScheduleRepository schrepo;

    private final HttpSession session;

    private ModelAndView mav;

    /**
     * 기존 테스트용 메서드: 선생님 ID로 모든 주소 가져오기
     */
    // 신청서에 선생님 아이디로 주소목록 불러오기 테스트
    public List<String> getCRAddressListTest(String tId) {
        // 신청서에 선생님 아이디 존재여부 확인
        Optional<ClassRequestEntity> checkTId = clrqrepo.findFirstBytId_tId(tId);
        // 주소를 담을 새로운 리스트 생성
        List<String> addresses = new ArrayList<>();
        // 존재하면
        if (checkTId.isPresent()) {
            // 모든 신청서를 담을 리스트 생성 및 불러오기
            List<ClassRequestEntity> clreqList = clrqrepo.findAllBytId_tId(tId);
            // 불러온 주소를 리스트에 담기
            for (ClassRequestEntity clreq : clreqList) {
                addresses.add(clreq.getClReqPlace());
            }
        }
        // 결과 확인
        System.out.println(addresses);
        return addresses;
    }


    @Transactional
    public Map<String, List<ClassRequestDTO>> getGroupedAddressesByDay(String tId) {
        // tId로 데이터를 조회
        List<ClassRequestEntity> clreqList = clrqrepo.findAllBytId_tIdOrderByClReqIdAsc(tId);

        // 데이터를 요일별로 그룹화하여 DTO 리스트로 반환
        return clreqList.stream()
                .collect(Collectors.groupingBy(
                        ClassRequestEntity::getClReqDay, // 요일 기준 그룹화
                        Collectors.mapping(clreq -> new ClassRequestDTO(
                                clreq.getClReqPlace(), // 주소
                                clreq.getSId().getSId(),
                                clreq.getClReqId()
                        ), Collectors.toList())
                ));
    }

}
