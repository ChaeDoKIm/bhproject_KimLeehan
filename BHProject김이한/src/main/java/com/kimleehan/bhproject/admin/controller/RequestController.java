package com.kimleehan.bhproject.admin.controller;

import com.kimleehan.bhproject.admin.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
public class RequestController {

    private final RequestService rrsvc;

    // 학습신청 목록
//    @GetMapping("/admin/clReqList")
//    public String clReqList(){
//        return "/admin/clReqList";
//    }


    // clReqList : 학습신청 목록
    @GetMapping("/admin/clReqList")
    public ModelAndView clReqList(){
        System.out.println("\n학습목록 메소드\n[1] html → controller");
        return rrsvc.clReqList();
    }

    // clReqId: 학습신청 상세보기
    @GetMapping("/admin/clReqView/{clReqId}")
    public ModelAndView clReqView(@PathVariable Long clReqId){
        System.out.println("\n학습신청 상세보기 메소드\n[1] html → controller");
        return rrsvc.clReqView(clReqId);
    }


}
