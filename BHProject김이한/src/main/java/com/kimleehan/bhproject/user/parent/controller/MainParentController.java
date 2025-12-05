package com.kimleehan.bhproject.user.parent.controller;

import com.kimleehan.bhproject.user.parent.dto.ParentInfoDTO;
import com.kimleehan.bhproject.user.parent.dto.StudentInfoDTO;
import com.kimleehan.bhproject.user.parent.service.ParentInfoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainParentController {

    private final ParentInfoService psvc;

    private final HttpSession session;

    // 회원가입 메소드
    @PostMapping("/pJoin")
    public ModelAndView pJoin(@ModelAttribute ParentInfoDTO parent) {
        System.out.println("\n회원가입 메소드\n[1] html → controller || member : " + parent);
        return psvc.pJoin(parent);
    }

    // 로그인 메소드
    @PostMapping("/pLogin")
    public ModelAndView pLogin(@ModelAttribute ParentInfoDTO parent) {
        System.out.println("\n로그인 메소드\n[1] html → controller : " + parent);
        return psvc.pLogin(parent);
    }

//    // 학습신청 보기 페이지
//    @GetMapping("/parent/myClassRequests")
//    public String myClassRequestsPage() {
//        return "/parent/myClassRequests";
//    }


    // 학부모 내정보 보기 메소드
    @GetMapping("/myChildP/{pId}")
    public ModelAndView pView(@PathVariable String pId) {
        System.out.println("\n회원정보 보기\n[1] html → controller; pId : " + pId);
        return psvc.pView(pId);
    }

    @GetMapping("/parent/myInfoP/{pId}")
    public ModelAndView myInfoP(@PathVariable String pId) {
        System.out.println("\n내정보 보기\n[1] html → controller; pId : " + pId);
        return psvc.myInfoP(pId);
    }

    // 학생/자녀 등록 메소드
    @PostMapping("/sAddChild")
    public ResponseEntity<Map<String, String>> sAddChild(@ModelAttribute StudentInfoDTO student) {
        System.out.println("\n학생 등록\n[1] html → controller; student : " + student);

        boolean isSuccess = psvc.sAddChild(student); // 서비스 호출

        Map<String, String> response = new HashMap<>();

        if (isSuccess) {
            response.put("message", "학생 등록이 완료되었습니다!");
            return ResponseEntity.ok(response); // HTTP 200 OK
        } else {
            response.put("message", "학생 등록에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // HTTP 400 BAD REQUEST
        }
    }

    // 로그아웃 메소드
    @GetMapping("/pLogout")
    public String pLogout() {
        session.invalidate();
        return "redirect:/";
    }


}
