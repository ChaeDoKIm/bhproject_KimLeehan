package com.kimleehan.bhproject.user.teacher.controller;

import com.kimleehan.bhproject.user.teacher.dto.TeacherCommentDTO;
import com.kimleehan.bhproject.user.teacher.service.TeacherInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeacherRestController {

    private final TeacherInfoService tsvc;

    @PostMapping("/tCommentList")
    public List<TeacherCommentDTO> getCommentList(@RequestParam("clProgId") Long clProgId){
        System.out.println("tCommentList, check clProgId : " + clProgId);
        return tsvc.tCommentList(clProgId);
    }

    // emailCheck : 이메일 인증번호 받아오기
    @PostMapping("/tEmailCheck")
    public String tEmailCheck(@RequestParam("tEmail") String tEmail) {

        return tsvc.tEmailCheck(tEmail);
    }

}
