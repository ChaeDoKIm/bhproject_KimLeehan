package com.kimleehan.bhproject.user.teacher.controller;

import com.kimleehan.bhproject.user.teacher.dao.ScheduleRepository;
import com.kimleehan.bhproject.user.teacher.dto.TeacherCommentDTO;
import com.kimleehan.bhproject.user.teacher.dto.TeacherInfoDTO;
import com.kimleehan.bhproject.user.teacher.service.TeacherInfoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class MainTeacherController {

    private final TeacherInfoService tsvc;

    private final HttpSession session;
    private final ScheduleRepository scheduleRepository;

    // 선생님 로그인 메소드
    @PostMapping("/tLogin")
    public ModelAndView tLogin(@ModelAttribute TeacherInfoDTO teacher){
        System.out.println("선생님 로그인 메소드 확인 || teacher : " + teacher);
        return tsvc.tLogin(teacher);
    }

    // 선생님 정보 보기 메소드
    @GetMapping("/tMySchedule/{tId}")
    public ModelAndView tMySchedule(@PathVariable String tId) {
        System.out.println("선생님 정보 보기 메소드 확인 || tId:" + tId);
        return tsvc.tMySchedule(tId);
    }

    @GetMapping("/tStudents/{tId}")
    public ModelAndView tStudents(@PathVariable String tId) {
        System.out.println("선생님 정보 보기 메소드 확인 || tId:" + tId);
        return tsvc.tStudents(tId);
    }

    @GetMapping("/tMyInfo/{tId}")
    public ModelAndView tMyInfo(@PathVariable String tId) {
        System.out.println("선생님 정보 보기 메소드 확인 || tId:" + tId);
        return tsvc.tMyInfo(tId);
    }

    @PostMapping("/addComment")
    public ModelAndView addComment(@ModelAttribute TeacherCommentDTO teacherComment, @RequestParam Long sId, @RequestParam String clProgBook){
        System.out.println("addComment, check teacherComment : " + teacherComment);
        return tsvc.addComment(teacherComment, sId, clProgBook);
    }

    // To-Do 항목 추가
    @PostMapping("/tMySchedule/{tId}/add")
    public void addTodo(@PathVariable String tId, @RequestParam("todoItem") String todoItem) {
        System.out.println("To-Do tId: " + tId + ", 항목: " + todoItem);
    }

    @PostMapping("/modMyInfoT")
    public ModelAndView modMyInfoT(@ModelAttribute TeacherInfoDTO teacher, RedirectAttributes redirectAttributes) {
        System.out.println("선생님 정보 수정 check teacher : " + teacher);

        try {
            ModelAndView mav = tsvc.modInfoT(teacher);
            redirectAttributes.addFlashAttribute("message", "정보 수정이 완료되었습니다!");
            redirectAttributes.addFlashAttribute("alertType", "success");
            return mav;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "정보 수정에 실패했습니다. 다시 시도해주세요.");
            redirectAttributes.addFlashAttribute("alertType", "error");
            return new ModelAndView("redirect:/tMyInfo/" + teacher.getTId());
        }
    }

    @GetMapping("/tLogout")
    public String tLogout() {
        session.invalidate();
        return "teacher/Tmain";
    }


}
