package com.kimleehan.bhproject.admin.controller;

import com.kimleehan.bhproject.admin.service.ScheduleService;
import com.kimleehan.bhproject.user.teacher.dao.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService schsvc;
    private final ScheduleRepository scheduleRepository;

    @GetMapping("/admin/scheduleList")
    public ModelAndView getScheduleList() {
        return schsvc.getScheduleList();
    }

    @GetMapping("/sendPaymentInfo/{planId}")
    @ResponseBody
    public String sendPaymentInfo(@PathVariable Long planId) {
        System.out.println("결제 정보 저장 메소드 확인 : controller");
        return schsvc.sendPaymentInfo(planId);
    }

    @PostMapping("/parent/myclreqView/update-payment-status")
    public ResponseEntity<Map<String, Object>> updatePaymentStatus(@RequestBody Map<String, Object> request) {
        try {
            Long clPayId = Long.valueOf(request.get("clPayId").toString());
            String clStatus = request.get("clStatus").toString();

            System.out.println("🔍 요청 받은 결제 ID: " + clPayId + " 상태 변경: " + clStatus);

            boolean isUpdated = schsvc.updatePaymentStatus(clPayId, clStatus);

            Map<String, Object> response = new HashMap<>();
            response.put("success", isUpdated);
            response.put("message", isUpdated ? "결제 상태 업데이트 성공" : "결제 상태 업데이트 실패");

            return ResponseEntity.ok(response);
        } catch (NumberFormatException e) {
            System.err.println("🚨 잘못된 결제 ID 값: " + request.get("clPayId"));
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "잘못된 결제 ID 값"));
        } catch (Exception e) {
            System.err.println("🚨 서버 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "서버 오류 발생"));
        }
    }

    @GetMapping("/deleteClassReqAndPaymentInfo/{classReqId}")
    @ResponseBody
    public String deleteClassInfo (@PathVariable Long classReqId) {
        System.out.println("삭제 메소드 확인 || classReqId: " + classReqId);

        return schsvc.deleteClass(classReqId);
    }



}
