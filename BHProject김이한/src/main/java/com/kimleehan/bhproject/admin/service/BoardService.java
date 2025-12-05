package com.kimleehan.bhproject.admin.service;

import com.kimleehan.bhproject.admin.dao.FaqAnswerRepository;
import com.kimleehan.bhproject.admin.dao.FaqBoardRepository;
import com.kimleehan.bhproject.admin.dto.*;
import com.kimleehan.bhproject.user.parent.dao.ParentInfoRepository;
import com.kimleehan.bhproject.user.teacher.dao.TeacherInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Service
//@RequiredArgsConstructor
//public class BoardService {
//
//    private final FaqBoardRepository brepo;
//
//    private ModelAndView mav;
//
//    // 문의하기
//    public ModelAndView bWrite(FaqBoardDTO board) {
//        System.out.println("[2] controller → service : " + board);
//        mav = new ModelAndView();
//
//        try {
//            // DTO를 Entity로 변환 후 데이터 저장
//            FaqBoardEntity entity = FaqBoardEntity.toEntity(board);
//            brepo.save(entity);
//        } catch (Exception e) {
//            System.out.println("게시글 등록 실패: " + e.getMessage());
//        }
//
//        // 등록 후 메인 페이지로 리다이렉트
//        mav.setViewName("redirect:/main");
//
//        return mav;
//    }


@Service
@RequiredArgsConstructor
public class BoardService {

    private final FaqBoardRepository brepo;
    private final FaqAnswerRepository answerrepo;
    private final TeacherInfoRepository trepo;
    private final ParentInfoRepository prepo;
    private final HttpSession session;
    private final HttpServletRequest request;
    private Model model;

    private ModelAndView buildModelAndView(String viewName, String attributeName, Object data) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        if (attributeName != null && data != null) {
            mav.addObject(attributeName, data);
        }
        return mav;
    }

    // 문의 작성
    public ModelAndView bWrite(FaqBoardDTO board) {
        try {
            FaqBoardEntity entity = FaqBoardEntity.toEntity(board);
            // 비회원 (아이디가 없다면)
            if (!entity.getQCategory().equals("비회원")) {
                entity.setQPass("회원");
            }
            entity.setQDate(Instant.now()); // 작성일 자동 설정
            entity.setQStatus("미확인");

            brepo.save(entity);

            //비회원이라면
            if (entity.getQCategory().startsWith("선생님")) {
                return buildModelAndView("redirect:/teacher/Tmain", null, null);
            } else {
                return buildModelAndView("redirect:/main", null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return buildModelAndView("error", "message", "문의 작성 중 문제가 발생했습니다.");
        }
    }

    // 문의 목록
    public ModelAndView bList() {
        try {
            List<FaqBoardDTO> boardList = brepo.findAll().stream()
                    .map(FaqBoardDTO::toDTO)
                    .collect(Collectors.toList());
            return buildModelAndView("board/list", "boardList", boardList);
        } catch (Exception e) {
            e.printStackTrace();
            return buildModelAndView("error", "message", "문의 목록 조회 중 문제가 발생했습니다.");
        }
    }

    // 답변 작성
    @Transactional
    public ModelAndView addAnswer(FaqAnswerDTO answer) {
        try {
            // (1) 세션에서 admin 정보 확인
            String admin = (String) session.getAttribute("adminLoginId");

            if (admin == null) { // 로그인되지 않은 경우
                System.out.println("관리자 권한이 필요합니다.");
                return buildModelAndView("error", "message", "관리자 로그인이 필요합니다.");
            }

            // (2) DTO → Entity 변환
            FaqAnswerEntity entity = FaqAnswerEntity.toEntity(answer);
            entity.setAnsDate(Instant.now()); // 작성일 자동 설정

            // (3) 작성자(admin) 정보 설정
            AdminInfoEntity adminEntity = new AdminInfoEntity();
            adminEntity.setAId(admin); // 세션에서 가져온 관리자 ID 설정
            entity.setAId(adminEntity);

            // (4) 답변 저장
            brepo.updateAnswerStatus(entity.getQId().getQId());
            answerrepo.save(entity);

            // 성공 시 해당 문의글 상세 페이지로 리다이렉트
            return buildModelAndView("redirect:/board/view/" + answer.getQId(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return buildModelAndView("error", "message", "답변 저장 중 문제가 발생했습니다.");
        }
    }

    // 문의글 상세보기 - 관리자용
    @Transactional
    public ModelAndView bView(Long qId) {
        try {
            // 문의글 엔터티 가져오기
            var boardEntity = brepo.findById(qId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문의글입니다."));
            FaqBoardDTO boardDto = FaqBoardDTO.toDTO(boardEntity);

            // 문의글 ID를 기준으로 답변 가져오기
            List<FaqAnswerDTO> answers = answerrepo.findByqId(boardEntity).stream()
                    .map(FaqAnswerDTO::toDTO)
                    .collect(Collectors.toList());

            // ModelAndView 생성
            ModelAndView mav = new ModelAndView();

            mav.setViewName("board/view");
            mav.addObject("board", boardDto);
            mav.addObject("answers", answers);
            return mav;
        } catch (Exception e) {
            e.printStackTrace();
            return buildModelAndView("error", "message", "문의 상세 조회 중 문제가 발생했습니다.");
        }
    }


    // 문의글 상세보기 - 회원용
    @Transactional
    public ModelAndView bViewForUser(Long qId) {
        try {
            var boardEntity = brepo.findById(qId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문의글입니다."));
            FaqBoardDTO boardDto = FaqBoardDTO.toDTO(boardEntity);

            List<FaqAnswerDTO> answers = answerrepo.findByqId(boardEntity).stream()
                    .map(FaqAnswerDTO::toDTO)
                    .collect(Collectors.toList());

            ModelAndView mav = new ModelAndView();
            mav.setViewName("board/view2");   // 🔥 회원용 새 템플릿
            mav.addObject("board", boardDto);
            mav.addObject("answers", answers);
            return mav;
        } catch (Exception e) {
            e.printStackTrace();
            return buildModelAndView("error", "message", "문의 상세 조회 중 문제가 발생했습니다.");
        }
    }




    // 회원/비회원 문의 확인
    public ModelAndView myFaq(String loginId) {
        System.out.println("[2] controller → service || loginId : " + loginId);

        Optional<FaqBoardEntity> checkBoard = brepo.findFirstByqWriter(loginId);
        if (checkBoard.isPresent()) {
            List<FaqBoardEntity> boardListEntity = brepo.findAllByqWriter(loginId);
            List<FaqBoardDTO> boardList = new ArrayList<>();
            for(FaqBoardEntity entity : boardListEntity) {
                FaqBoardDTO boardDto = FaqBoardDTO.toDTO(entity);
                boardList.add(boardDto);
                
            }
            return buildModelAndView("/board/myfaq", "boardList", boardList);
        } else {
            return buildModelAndView("redirect:/main", "message", "해당 아이디로 작성된 글이 없습니다");
        }
    }

    // 비회원 문의 확인용 메소드
    public String nMemFAQ(String qId, String qPass) {
        String result = "NONE";
        Optional<FaqBoardEntity> checkBoard = brepo.findFirstByqWriter(qId);
        if (checkBoard.isPresent()) {
            if (checkBoard.get().getQPass().equals(qPass)) {
                result = "OK";
            } else {
                result = "NO";
            }
        }
        return result;
    }

    // 문의 글 삭제
    public boolean delete(Long qId) {
        System.out.println("controller -> service || 문의 삭제 확인: " + qId);
        try {
            if (brepo.existsById(qId)) {
                System.out.println("문의 글 존재");
                brepo.deleteById(qId);
                return true; // Successfully deleted
            }
            return false; // ID not found
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("문의 삭제 중 오류 발생.");
        }
    }
}
