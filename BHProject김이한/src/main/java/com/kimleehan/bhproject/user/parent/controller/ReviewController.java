package com.kimleehan.bhproject.user.parent.controller;

import com.kimleehan.bhproject.user.parent.dto.ReviewDTO;
import com.kimleehan.bhproject.user.parent.service.ReviewService;
import com.kimleehan.bhproject.user.teacher.service.ClassProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewSvc;
    private final ClassProgressService cpsvc;


    // 리뷰 조회
    @GetMapping("/parent/teacherInfo/review/{tId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByTeacher(@PathVariable String tId) {
        System.out.println(" 리뷰 조회 요청 - tId: " + tId);

        List<ReviewDTO> reviews = reviewSvc.getReviewsByTeacher(tId);

        if (reviews == null || reviews.isEmpty()) {
            System.out.println("  해당 선생님에 대한 리뷰 없음.");
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(reviews);
    }


    // 리뷰 작성
    @PostMapping("/parent/myChild/review/save")
    public ResponseEntity<Map<String, String>> saveReview(@RequestBody Map<String, Object> requestData) {

        Long sId = requestData.get("sId") != null ? Long.valueOf(requestData.get("sId").toString()) : null;
        String tId = requestData.get("tId") != null ? requestData.get("tId").toString() : null;
        Long revRate = requestData.get("revRate") != null ? Long.valueOf(requestData.get("revRate").toString()) : null;

        Map<String, String> response = new HashMap<>();
        if (sId == null) {
            System.out.println("sId : NULL");
            response.put("message", "SID 누락");
            return ResponseEntity.badRequest().body(response);
        }

        // `tId` 조회 로직 실행
        String fetchedTId = reviewSvc.getTeacherIdForReview(sId);
        System.out.println(" tId: " + fetchedTId);
        if (fetchedTId == null) {
            System.out.println(" tId 조회 실패 ->  리뷰 저장 불가.");
            response.put("message", "진행 중인 수업이 없어 리뷰를 작성할 수 없습니다.");
            return ResponseEntity.badRequest().body(response);
        }

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setSId(sId);
        reviewDTO.setTId(fetchedTId);
        reviewDTO.setRevRate(revRate);

        reviewSvc.saveReview(reviewDTO);

        response.put("message", "리뷰가 등록되었습니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/parent/myChild/review/check/{sId}/{tId}")
    public ResponseEntity<Integer> checkReview(@PathVariable Long sId, @PathVariable String tId) {
        int reviewCount = reviewSvc.getReviewCount(sId, tId);
        return ResponseEntity.ok(reviewCount);
    }


}
