package com.kimleehan.bhproject.admin.controller;

import com.kimleehan.bhproject.admin.dto.TeacherSpecDTO;
import com.kimleehan.bhproject.admin.service.TeacherSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class TeacherSpecController {

    private final TeacherSpecService tssvc;

    @PostMapping("/addSpec")
    public ModelAndView addSpec(TeacherSpecDTO spec) {
        System.out.println("[1] html → controller || spec :" + spec);
        return tssvc.addSpec(spec);
    }

    @GetMapping("/admin/specList")
    public ModelAndView getSpecList() {
        return tssvc.getSpecList();
    }

    @GetMapping("/tViewSpec/{tSid}")
    public ModelAndView tViewSpec(@PathVariable Long tSid) {
        return tssvc.tViewSpec(tSid);
    }

    @GetMapping("/admin/updateStatus")
    @ResponseBody
    public String updateStatus(@RequestParam Long tSid) {
        return tssvc.updateStatus(tSid);
    }
}
