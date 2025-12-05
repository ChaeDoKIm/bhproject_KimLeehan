package com.kimleehan.bhproject.user.parent.controller;


import com.kimleehan.bhproject.user.parent.dto.ClassRequestDTO;
import com.kimleehan.bhproject.user.parent.dto.ParentInfoEntity;
import com.kimleehan.bhproject.user.parent.dto.StudentInfoDTO;
import com.kimleehan.bhproject.user.parent.service.ClassRequestService;
import com.kimleehan.bhproject.user.parent.service.ParentInfoService;
import com.kimleehan.bhproject.user.teacher.dto.TeacherInfoDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class ClassRequestController {
    private final ClassRequestService crsvc;
    private final ParentInfoService psvc;

    private HttpSession session;


    @GetMapping("/findTeachers")
    public ResponseEntity<List<TeacherInfoDTO>> findTeachers(
            @RequestParam String address,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String grade,
            @RequestParam(required = false) String level) {

        List<TeacherInfoDTO> teachers = crsvc.findTeachersByCriteria(address, subject, grade, level)
                .stream()
                .map(TeacherInfoDTO::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(teachers);
    }


    @GetMapping("/getTeacherInfo")
    public ResponseEntity<TeacherInfoDTO> getTeacherInfo(@RequestParam String tId) {
        TeacherInfoDTO teacherInfo = crsvc.findTeacherById(tId);
        return ResponseEntity.ok(teacherInfo);
    }



    /* 선생님 선택 -> 학습신청(선생님 정보) */
    @GetMapping("/parent/teacherInfo/{tId}")
    public ModelAndView tInfoView(@PathVariable String tId, HttpSession session) {
        ModelAndView mav = crsvc.tInfoView(tId);

        // 학부모 정보 가져오기
        String loginId = (String) session.getAttribute("parentLoginId");
        ParentInfoEntity parent = crsvc.findParentById(loginId);

        // 학생 정보 가져오기
        List<StudentInfoDTO> studentList = crsvc.findStudentsByParentId(loginId);

        // Model에 추가
        mav.addObject("parentInfo", parent);
        mav.addObject("studentList", studentList);

        return mav;
    }


    // 학습 신청 저장
    @PostMapping("/parent/submitClassRequest")
    public ModelAndView submit(@ModelAttribute ClassRequestDTO classRequest) {
        System.out.println("\n학습신청\n[1] submit : " + classRequest);
        return crsvc.submit(classRequest);
    }

    // 내 학습 신청 목록
    @GetMapping("/parent/myClassRequests/{pId}")
    public ModelAndView myClassRequests(@PathVariable String pId) {
        System.out.println("\n학습신청 보기\n[1] html → controller; pId : " + pId);
        return crsvc.myClassRequests(pId);
    }

    @GetMapping("/viewClassRequest/{clReqId}")
    public ModelAndView viewClassRequest(@PathVariable Long clReqId){
        System.out.println("\n학습신청 상세보기\n[1] html → controller; clReqId : " + clReqId);
        return crsvc.viewClassRequest(clReqId);
    }

    // 신청 마감 기능 추가
    // 특정 선생님의 마감 요일 조회 API
    @GetMapping("/teacherInfo/closedDays/{tId}")
    public ResponseEntity<List<String>> getClosedDays(@PathVariable String tId) {
        List<String> closedDays = crsvc.getClosedDaysForTeacher(tId);
        return ResponseEntity.ok(closedDays);
    }
}