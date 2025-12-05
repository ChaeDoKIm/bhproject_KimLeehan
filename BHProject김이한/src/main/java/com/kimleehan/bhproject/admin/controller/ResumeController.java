package com.kimleehan.bhproject.admin.controller;

import com.kimleehan.bhproject.admin.dto.ResumeRequestDTO;
import com.kimleehan.bhproject.admin.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.core.io.Resource;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {
    private final ResumeService rService;

    // 입사지원 작성 페이지 이동
    @GetMapping("/rwrite")
    public String rWriteForm() {
        return "resume/rwrite";
    }

    // 입사지원 제출
    @PostMapping("/submit")
    public ModelAndView submitResume(@ModelAttribute ResumeRequestDTO resume) {
        return rService.submitResume(resume);
    }

//    // 입사지원서 목록 페이지
//    @GetMapping("/list")
//    public ModelAndView list() {
//        return rService.list();
//    }
//
//    // rView : 입사지원서 상세보기
//    @GetMapping("/rView/{resId}")
//    public ModelAndView rView(@PathVariable Long resId) {
//        return rService.rView(resId);
//    }


    // 이력서파일 다운로드 기능 추가
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {

            Resource resource = rService.downloadResume(fileName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 수정 중 (기능 업데이트 중)
    // 1) 이력서 목록
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("resumeList", rService.findAll());
        return "/resume/list";   // templates/admin/resume/list.html
    }

    // 2) 이력서 상세
    @GetMapping("/rView/{resId}")
    public String view(@PathVariable Long resId, Model model) {
        ResumeRequestDTO resume = rService.findById(resId);
        model.addAttribute("resume", resume);
        return "/resume/view";   // templates/admin/resume/view.html
    }

    // 3) 상태 변경 (합격 / 대기 / 반려)
    @PostMapping("/status")
    public String changeStatus(@RequestParam Long resId,
                               @RequestParam String status) {

        // 복잡한 로직은 Service에서 처리
        rService.changeStatus(resId, status);

        // 다시 상세 화면으로 리다이렉트
        return "redirect:/resume/rView/" + resId;
    }





}



