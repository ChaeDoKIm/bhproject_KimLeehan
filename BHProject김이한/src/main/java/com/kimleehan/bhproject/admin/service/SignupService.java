package com.kimleehan.bhproject.admin.service;

import com.kimleehan.bhproject.admin.dao.SignupRequestRepository;
import com.kimleehan.bhproject.admin.dto.SignupRequestDTO;
import com.kimleehan.bhproject.admin.dto.SignupRequestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final SignupRequestRepository signupRepo;

    // 공통
    private ModelAndView buildModelAndView(String viewName, String attributeName, Object data) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        if (attributeName != null && data != null) {
            mav.addObject(attributeName, data);
        }
        return mav;
    }

    // 상담 신청 저장
    public ModelAndView submit(SignupRequestDTO signupRequestDTO) {
        try {

            SignupRequestEntity entity = SignupRequestEntity.toEntity(signupRequestDTO);
            entity.setSReqStatus("미확인"); // 기본 상태 설정
            entity.setSReqDate(LocalDate.now()); // 제출날짜 추가
            signupRepo.save(entity);
            return buildModelAndView("redirect:/main", null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return buildModelAndView("error", "message", "상담 신청 저장 중 문제가 발생했습니다.");
        }
    }

    // 상담 신청 목록 조회
    public ModelAndView list() {
        try {
            var dtoList = signupRepo.findAll().stream()
                    .map(SignupRequestDTO::toDTO)
                    .toList();
            return buildModelAndView("signup/list", "signupList", dtoList);
        } catch (Exception e) {
            e.printStackTrace();
            return buildModelAndView("error", "message", "상담 신청 목록 조회 중 문제가 발생했습니다.");
        }
    }

    // 상담 신청 상세보기
    public ModelAndView view(Long sReqId) {
        try {
            Optional<SignupRequestEntity> entity = signupRepo.findById(sReqId);
            if (entity.isPresent()) {
                var dto = SignupRequestDTO.toDTO(entity.get());
                return buildModelAndView("signup/view", "signup", dto);
            } else {
                return buildModelAndView("redirect:/signup/list", "message", "존재하지 않는 상담 신청입니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return buildModelAndView("error", "message", "상담 신청 상세 조회 중 문제가 발생했습니다.");
        }
    }
}
