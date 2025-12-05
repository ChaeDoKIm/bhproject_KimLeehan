package com.kimleehan.bhproject.admin.controller;

import com.kimleehan.bhproject.admin.dto.SignupRequestDTO;
import com.kimleehan.bhproject.admin.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignupController {
    private final SignupService signupService;

    // 상담 신청 작성 페이지 이동
    @GetMapping("/form")
    public String form() {
        return "signup/form";
    }

    // 상담 신청 저장
    @PostMapping("/submit")
    public ModelAndView submit(@ModelAttribute SignupRequestDTO signupRequestDTO) {
        return signupService.submit(signupRequestDTO);
    }

    // 상담 신청 목록 페이지
    @GetMapping("/list")
    public ModelAndView list() {
        return signupService.list();
    }

    // 상담 신청서 상세보기
    @GetMapping("/view/{sReqId}")
    public ModelAndView view(@PathVariable Long sReqId) {
        return signupService.view(sReqId);
    }
}
