package com.kimleehan.bhproject.user.parent.controller;

import com.kimleehan.bhproject.user.parent.service.ParentInfoService;
import com.kimleehan.bhproject.user.teacher.dto.TeacherCommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParentRestController {

    private final ParentInfoService psvc;

    // 아이디 확인 메소드
    @PostMapping("/idCheck")
    public String idCheck(@RequestParam("pId") String pId) {

        return psvc.idCheck(pId);
    }

    // emailCheck : 이메일 인증번호 받아오기
    @PostMapping("/emailCheck")
    public String emailCheck(@RequestParam("pEmail") String pEmail) {

        return psvc.emailCheck(pEmail);
    }

    @PostMapping("/showComments/{clProgId}")
    public List<TeacherCommentDTO> showComments(@PathVariable Long clProgId) {
        return psvc.showComments(clProgId);
    }

//    @PostMapping("parent/myChildInfo")
//    public List<StudentClassInfoDTO> getStudentInfo(@RequestParam("pId") String pId) {
//        return psvc.getStudentInfo(pId);
//    }

    // 학생 목록 불러오기
//    @PostMapping("/studentList/{pId}")
//    public List<StudentInfoDTO> studentList(@PathVariable String pId) {
//        return psvc.studentList(pId);
//    }

}
