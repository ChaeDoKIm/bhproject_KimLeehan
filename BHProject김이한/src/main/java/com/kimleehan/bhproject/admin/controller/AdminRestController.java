package com.kimleehan.bhproject.admin.controller;

import com.kimleehan.bhproject.admin.service.ResumeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resume")
public class AdminRestController {

    private final ResumeService rService;

    public AdminRestController(ResumeService rService) {
        this.rService = rService;
    }

    // emailCheck : 이메일 인증번호 받아오기
    @PostMapping("/emailCheck")
    public String emailCheck(@RequestParam("resEmail") String resEmail) {

        String uuid = rService.emailCheck(resEmail);

        return uuid;
    }


}
