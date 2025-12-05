package com.kimleehan.bhproject.admin.service;


import com.kimleehan.bhproject.user.parent.dao.ClassRequestRepository;
import com.kimleehan.bhproject.user.parent.dto.ClassRequestDTO;
import com.kimleehan.bhproject.user.parent.dto.ClassRequestEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    // 학습신청 repository
    private final ClassRequestRepository crRepo;


    private ModelAndView mav;


    @Transactional
    public ModelAndView clReqList() {
        System.out.println("[2] controller → service ");
        mav = new ModelAndView();

        try {
            // (1) DB에서 지원서 리스트 조회 (Entity 타입)
            List<ClassRequestEntity> entityList = crRepo.findAll(Sort.by(Sort.Direction.ASC, "clReqId"));

            // (+) 지연 로딩된 연관 엔티티 초기화
            entityList.forEach(entity -> {
                Hibernate.initialize(entity.getSId());
                Hibernate.initialize(entity.getPId());
                Hibernate.initialize(entity.getTId());
            });

            // (2) Entity 리스트를 DTO 리스트로 변환
            List<ClassRequestDTO> dtoList = entityList.stream()
                    .map(ClassRequestDTO::toDTO)
                    .toList();

            // (3) ModelAndView에 데이터 추가
            mav.setViewName("/admin/clReqList"); // 목록 페이지 경로
            mav.addObject("clReqList", dtoList);
        } catch (Exception e) {
            // 예외 처리
            mav.setViewName("error");
            mav.addObject("message", "목록을 불러오는 중 문제가 발생했습니다.");
            e.printStackTrace();
        }

        return mav;
    }

    @Transactional
    public ModelAndView clReqView(Long clReqId) {
        mav = new ModelAndView();
        try {
            // (1) 데이터 조회
            Optional<ClassRequestEntity> optionalEntity = crRepo.findById(clReqId);

            if (optionalEntity.isPresent()) {
                ClassRequestEntity entity = optionalEntity.get();

                // (2) 지연 로딩된 연관 엔티티 초기화
                Hibernate.initialize(entity.getSId());
                Hibernate.initialize(entity.getPId());
                Hibernate.initialize(entity.getTId());

                // (3) DTO 변환
                ClassRequestDTO clReq = ClassRequestDTO.toDTO(entity);

                // (4) ModelAndView에 데이터 추가
                mav.addObject("clReq", clReq);
                mav.setViewName("/admin/clReqView");
            } else {
                // 데이터가 없는 경우
                mav.setViewName("redirect:/admin/clReqList");
                mav.addObject("message", "존재하지 않는 신청서입니다.");
            }
        } catch (Exception e) {
            // 예외 처리
            mav.setViewName("error");
            mav.addObject("message", "신청서 조회 중 문제가 발생했습니다.");
            e.printStackTrace();
        }
        return mav;
    }


}
