package com.kimleehan.bhproject.admin.controller;


import com.kimleehan.bhproject.admin.dto.FaqAnswerDTO;
import com.kimleehan.bhproject.admin.dto.FaqBoardDTO;
import com.kimleehan.bhproject.admin.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService bsvc;
    private final HttpSession session;

    // bWriteForm : 문의작성 페이지 이동
    @GetMapping("/bWriteForm")
    public String bWriteForm() {
        return "/write";
    }

    // bWriteFormT
    @GetMapping("/bWriteFormT")
    public String bWriteFormT() {
        return "/teacher/write";
    }


    // bWrite : 문의 작성
    @PostMapping("/bWrite")
    public ModelAndView bWrite(@ModelAttribute FaqBoardDTO board) {
        return bsvc.bWrite(board);
    }

    @GetMapping("/board/myfaq/{loginId}")
    public ModelAndView myFaq(@PathVariable String loginId) {
        System.out.println("회원용 문의 작성 확인 || loginId:" + loginId);
        return bsvc.myFaq(loginId);
    }

    // 문의 목록
    @GetMapping("/board/list")
    public ModelAndView bList() {
        return bsvc.bList();
    }

    // bView : 문의 상세보기
    @GetMapping("/board/view/{qId}")
    public ModelAndView bView(@PathVariable Long qId) {
        return bsvc.bView(qId);
    }


    // bView : 문의 상세보기 - 회원용
    // 회원용 문의 상세보기
    @GetMapping("/board/view2/{qId}")
    public ModelAndView bViewForUser(@PathVariable Long qId) {
        return bsvc.bViewForUser(qId);
    }




    @PostMapping("/board/answer")
    public String addAnswer(@ModelAttribute FaqAnswerDTO answer) {
        bsvc.addAnswer(answer);
        return "redirect:/board/view/" + answer.getQId();
    }

    @PostMapping("/board/noMemFAQ")
    @ResponseBody
    public String nMemFAQ(@RequestParam String qId, String qPass) {
        return bsvc.nMemFAQ(qId, qPass);
    }

    // 문의 삭제
    @DeleteMapping("/board/delete/{qId}")
    public ResponseEntity<?> delete(@PathVariable Long qId) {
        System.out.println(" ajax -> controller 문의 삭제 메소드 확인: " + qId);
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isDeleted = bsvc.delete(qId);
            response.put("success", isDeleted);

            if (isDeleted) {
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "게시글을 찾을 수 없습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "서버 오류 발생");
            return ResponseEntity.internalServerError().body(response);
        }
    }

}












