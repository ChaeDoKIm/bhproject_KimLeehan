package com.kimleehan.bhproject.admin.controller;

import com.kimleehan.bhproject.admin.dto.AdminInfoDTO;
import com.kimleehan.bhproject.admin.service.AdminInfoService;
import com.kimleehan.bhproject.user.teacher.dto.TeacherInfoDTO;
import com.kimleehan.bhproject.user.teacher.service.TeacherInfoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MainAdminController {

    private final AdminInfoService asvc;
    private final TeacherInfoService tsvc;
    private final HttpSession session;

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }



    @GetMapping("/main")
    public ModelAndView mainPage(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        return new ModelAndView("main");
    }


    // addTForm : 선생님 등록 페이지
    @GetMapping("/addTForm")
    public String addTForm() {
        return "admin/form";
    }

    @GetMapping("/adminlogin")
    public String adminLogin() {
        return "adminlogin";
    }

    @GetMapping("admin/main")
    public String adminMain() {
        if (session.getAttribute("adminLoginId") == null) {
            return "redirect:/adminlogin";
        }
        return "admin/main";
    }

    // 주소 이동 확인용
//    @GetMapping("/admin/specList")
//    public String specList() {
//        return "admin/teacherSpecList";
//    }

    // aLogin : 로그인
    @PostMapping("/aLogin")
    public ModelAndView aLogin(@ModelAttribute AdminInfoDTO admin) {
        System.out.println("\n관리자 로그인 메소드\n[1] html → controller : " + admin);
        return asvc.aLogin(admin);
    }

    // 신규 선생님 등록 메소드
    @PostMapping("/addT")
    public ModelAndView addT(@ModelAttribute TeacherInfoDTO teacher) {
        System.out.println("\n신규 선생님 등록 메소드\n[1] html → controller : " + teacher);
        return tsvc.addT(teacher);
    }

    //선생님 목록
    @GetMapping("/manageTList")
    public ModelAndView manageT() {
        System.out.println("\n선생님 목록\n[1] html → controller");
        return tsvc.manageTList();
    }

    // 관리자 측 선생님 정보 수정 폼
    @GetMapping("/modTForm/{tId}")
    public ModelAndView modT(@PathVariable String tId) {
        System.out.println("\n선생님 상세보기\n[1] html → controller : " + tId);
        ModelAndView mav = tsvc.tMyInfo(tId);
        mav.setViewName("/admin/modTInfo");
        return mav;
    }

    // 관리자 측 선생님 정보 수정
    @PostMapping("/modTInfo")
    public ModelAndView modTInfo(@ModelAttribute TeacherInfoDTO teacher) {
        System.out.println("\n선생님 정보 수정\n[1] html → controller : " + teacher);
        ModelAndView mav = tsvc.modInfoT(teacher);
        mav.setViewName("redirect:/modTForm/" + teacher.getTId());
        return mav;
    }



    //aLogout : 로그아웃
    @GetMapping("/aLogout")
    public String aLogout() {
        session.invalidate();
        return "main";
    }
}
