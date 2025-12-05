package com.kimleehan.bhproject.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageMoveController {

    private final HttpSession session;
    private final HttpServletRequest request;

    // 메인 페이지
//    @GetMapping("/")
//    public String mainPage() {
//        return "main";
//    }
//
//    // 메인 페이지로 돌아가기 위한 주소
//    @GetMapping("/main")
//    public String main() {
//        return "main";
//    }

    // 회원가입 페이지
    @GetMapping("/pJoinForm")
    public String joinForm() {
//        if(request.getUserPrincipal() != null) {
//            return "redirect:/main";
//        }
        return "join";
    }

//    // 로그인 페이지
//    @GetMapping("/loginForm")
//    public String login() {
//        return "login";
//    }

    // 관리자 로그인 페이지
//    @GetMapping("/adminlogin")
//    public String adminLogin(){
//        return "adminlogin";
//    }

    @GetMapping("teacher/myPageT")
    public String teacherMyPage(){

        return "mySchedule";
    }

    @GetMapping("/tLoginForm")
    public String teacherLogin() {
//        if(session.getAttribute("teacherLoginId") != null) {
//            return "redirect:/teacher/Tmain";
//        }
        return "teacher/login";
    }

    @GetMapping("/pLoginForm")
    public String parentLogin() {
//        if(session.getAttribute("parentLoginId") != null) {
//            return "redirect:/main";
//        }
        return "login";
    }



    @GetMapping("/addChildForm")
    public String addChildForm(){
        return "/parent/addChild";
    }

    // 학습신청페이지 이동
    @GetMapping("/parent/classRequest")
    public String classRequest(){
        return "/parent/classRequest";
    }

    // 선생님 Main페이지
    @GetMapping("teacher/Tmain")
    public String teacherMain(){
        return "/teacher/Tmain";
    }

    @GetMapping("/teacher/myStudents")
    public String teacherStudents() {
        return "/teacher/myStudents";
    }

    @GetMapping("/teacher/myInfo")
    public String teacherInfo() {
        return "myInfoT";
    }

    // 로딩 페이지
    @GetMapping("/BHstart")
    public String loadingScreen() {
        return "BHstart";
    }
    // 결제페이지 TEST중
    @GetMapping("/payment")
    public String payment() {
        return "payment";
    }

}
