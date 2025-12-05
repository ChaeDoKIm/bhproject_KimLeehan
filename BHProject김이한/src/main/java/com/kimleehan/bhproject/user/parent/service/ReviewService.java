package com.kimleehan.bhproject.user.parent.service;

import com.kimleehan.bhproject.user.parent.dao.ReviewRepository;
import com.kimleehan.bhproject.user.parent.dao.StudentInfoRepository;
import com.kimleehan.bhproject.user.parent.dto.ReviewDTO;
import com.kimleehan.bhproject.user.parent.dto.ReviewEntity;
import com.kimleehan.bhproject.user.parent.dto.StudentInfoEntity;
import com.kimleehan.bhproject.user.teacher.dao.TeacherInfoRepository;
import com.kimleehan.bhproject.user.teacher.dto.TeacherInfoEntity;
import com.kimleehan.bhproject.user.teacher.service.ClassProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepo;
    private final TeacherInfoRepository trepo;
    private final StudentInfoRepository srepo;
    private final ClassProgressService cpsvc;


    // 리뷰 조회
    @Transactional
    public List<ReviewDTO> getReviewsByTeacher(String tId) {
        List<ReviewEntity> reviews = reviewRepo.findBytId_tId(tId);
        return reviews.stream().map(ReviewDTO::fromEntity).collect(Collectors.toList());
    }


    // 리뷰 저장
    @Transactional
    public void saveReview(ReviewDTO reviewDTO) {
        System.out.println(" 요청 받음");
        System.out.println(" 학생 ID: " + reviewDTO.getSId());
        System.out.println(" 선생님 ID: " + reviewDTO.getTId());
        System.out.println(" 별점: " + reviewDTO.getRevRate());

        if (reviewDTO.getSId() == null || reviewDTO.getTId() == null) {
            throw new IllegalArgumentException("학생 ID 또는 선생님 ID가 없습니다.");
        }

        // 학생 정보 조회
        StudentInfoEntity student = srepo.findById(reviewDTO.getSId())
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 존재하지 않습니다. ID: " + reviewDTO.getSId()));

        // 선생님 정보 조회
        TeacherInfoEntity teacher = trepo.findById(reviewDTO.getTId())
                .orElseThrow(() -> new IllegalArgumentException("해당 선생님이 존재하지 않습니다. ID: " + reviewDTO.getTId()));

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setRevDate(Instant.now());
        reviewEntity.setRevRate(reviewDTO.getRevRate());
        reviewEntity.setSId(student);
        reviewEntity.setTId(teacher);

        reviewRepo.save(reviewEntity);
        System.out.println(" 리뷰 저장 완료!");
    }



    public String getTeacherIdForReview(Long sId) {
        System.out.println(" sId: " + sId);

        if (sId == null) {
            System.out.println(" sId : `null`입니다! 리뷰 저장 불가.");
            return null;
        }

        List<Map<String, Object>> teacherList = cpsvc.getTeacherListByStudent(sId);

        if (teacherList.isEmpty()) {
            System.out.println("해당 학생의 진행 중인 수업이 없음.");
            return null;
        }

        String tId = teacherList.get(0).get("tId").toString();
        System.out.println("조회된 tId: " + tId);
        return tId;
    }

    public int getReviewCount(Long sId, String tId) {
        return reviewRepo.countBysIdAndtId(sId, tId);
    }


}
