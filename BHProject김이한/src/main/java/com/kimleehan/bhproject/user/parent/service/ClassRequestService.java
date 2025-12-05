package com.kimleehan.bhproject.user.parent.service;


import com.kimleehan.bhproject.admin.dao.ClassPaymentRepository;
import com.kimleehan.bhproject.admin.dto.ClassPaymentDTO;
import com.kimleehan.bhproject.admin.dto.ClassPaymentEntity;
import com.kimleehan.bhproject.user.parent.dao.ClassRequestRepository;
import com.kimleehan.bhproject.user.parent.dao.ParentInfoRepository;
import com.kimleehan.bhproject.user.parent.dao.StudentInfoRepository;
import com.kimleehan.bhproject.user.parent.dto.*;
import com.kimleehan.bhproject.user.teacher.dao.ScheduleRepository;
import com.kimleehan.bhproject.user.teacher.dao.TeacherInfoRepository;
import com.kimleehan.bhproject.user.teacher.dto.ScheduleEntity;
import com.kimleehan.bhproject.user.teacher.dto.TeacherInfoDTO;
import com.kimleehan.bhproject.user.teacher.dto.TeacherInfoEntity;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ClassRequestService {

    // 선생님 repository
    private final TeacherInfoRepository tInfoRepo;

    // 학습신청 repository
    private final ClassRequestRepository crRepo;

    // 학습신청 결제 repository
    private final ClassPaymentRepository cpayrepo;

    // 학부모 repository
    private final ParentInfoRepository prepo;

    // 학생/자녀 repository
    private final StudentInfoRepository srepo;

    // 일정 repository
    private final ScheduleRepository schrepo;

    private ModelAndView mav;

    // 로그인 session
    private final HttpSession session;


    public List<TeacherInfoEntity> findTeachersByAddress(String address) {
        return tInfoRepo.findBytArea(address);
    }


    public List<TeacherInfoEntity> findTeachersByCriteria(String address, String subject, String grade, String level) {
        List<TeacherInfoEntity> teachers = tInfoRepo.findBytArea(address);

        // 과목, 학년, 레벨 필터링
        if (subject != null && !subject.isEmpty()) {
            teachers = teachers.stream()
                    .filter(teacher -> teacher.getTSubject().contains(subject))
                    .collect(Collectors.toList());
        }

        if (grade != null && !grade.isEmpty()) {
            teachers = teachers.stream()
                    .filter(teacher -> teacher.getTGrade().equals(grade))
                    .collect(Collectors.toList());
        }

        if (level != null && !level.isEmpty()) {
            teachers = teachers.stream()
                    .filter(teacher -> teacher.getTLevel().equals(level))
                    .collect(Collectors.toList());
        }

        return teachers;
    }

    public TeacherInfoDTO findTeacherById(String tId) {
        TeacherInfoEntity teacher = tInfoRepo.findById(tId)
                .orElseThrow(() -> new RuntimeException("해당 선생님 정보를 찾을 수 없습니다."));
        return TeacherInfoDTO.toDTO(teacher);
    }


    public ModelAndView tInfoView(String tId) {
        ModelAndView mav = new ModelAndView();
        Optional<TeacherInfoEntity> entity = tInfoRepo.findById(tId);

        if (entity.isPresent()) {
            TeacherInfoDTO teacher = TeacherInfoDTO.toDTO(entity.get());
            mav.addObject("tInfoView", teacher);
            mav.setViewName("parent/teacherInfo");
        } else {
            mav.setViewName("redirect:/main");
        }

        return mav;
    }

    public ParentInfoEntity findParentById(String pId) {
        return prepo.findById(pId)
                .orElseThrow(() -> new RuntimeException("학부모 정보를 찾을 수 없습니다."));
    }

    public List<StudentInfoDTO> findStudentsByParentId(String pId) {
        List<StudentInfoEntity> students = srepo.findAllBypId_pId(pId);
        return students.stream()
                .map(StudentInfoDTO::toDTO)
                .collect(Collectors.toList());
    }

    // 학습 신청
    public ModelAndView submit(ClassRequestDTO classRequest) {
        System.out.println("학습신청 메소드 = [2] controller → service : " + classRequest);
        mav = new ModelAndView();

        try {
            // 1. TeacherInfoEntity 조회
            TeacherInfoEntity teacher = tInfoRepo.findById(classRequest.getTId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found: " + classRequest.getTId()));

            // 1-1. 학생 id로 신청 존재 여부 확인
            Optional<ClassRequestEntity> exists = crRepo.findFirstBysId_sId(classRequest.getSId());
            if (exists.isPresent()) {
                // 1-2. 학생으로 신청한 건 존재시 에러
                mav.addObject("alertMessage", "이미 학습이 신청된 학생입니다.");
                mav.setViewName("redirect:/parent/teacherInfo");
            } else {
                // 2. ClassRequestEntity 생성 및 TeacherInfo 설정
                ClassRequestEntity entity = ClassRequestEntity.toEntity(classRequest);
                entity.setClReqStatus("미확인"); // 기본 상태 설정
                entity.setTId(teacher); // 조회된 TeacherInfoEntity 설정
                // 3. 저장
                crRepo.save(entity);
                mav.setViewName("redirect:/parent/myClassRequests");

                // 4. 로그인한 학부모 ID 가져오기 (세션에서)
                String pId = (String) session.getAttribute("parentLoginId"); // 세션에서 pId 가져오기
                if (pId == null) {
                    // pId가 없으면 기본 myInfoP 페이지로 이동
                    mav.setViewName("redirect:/parent/myClassRequests");
                } else {
                    // pId가 있으면 동적으로 URL 설정
                    mav.setViewName("redirect:/parent/myInfoP/" + pId);
                }

            }
        } catch (Exception e) {
            System.err.println("Error saving ClassRequestEntity: " + e.getMessage());
            mav.setViewName("redirect:/parent/teacherInfo");
            throw new RuntimeException(e);
        }
        return mav;
    }

    // 내 학습 신청 불러오기 메소드
    @Transactional
    public ModelAndView myClassRequests(String pId) {
        System.out.println("학습 신청 불러오기 메소드 || [2] controller → service || pId : " + pId);
        mav = new ModelAndView();

        List<ClassRequestDTO> dtoList = new ArrayList<>();
        List<ClassRequestEntity> entityList = crRepo.findAllBypId_pId(pId);

        // dto에 해당 정보 저장
        for (ClassRequestEntity entity : entityList) {
            ClassRequestDTO dto = ClassRequestDTO.toDTO(entity);
            dtoList.add(dto);
        }

        mav.addObject("classRequests", dtoList);
        mav.setViewName("parent/myclreqList");

        return mav;
    }

    // 학습신청 상세보기
    @Transactional
    public ModelAndView viewClassRequest(Long clReqId) {
        System.out.println("학습신청 및 결제 정보 상세보기 = [2] controller → service || clReqId : " + clReqId);
        mav = new ModelAndView();

        // 사용할 코드, 삭제 X
        // ClassPaymentEntity clpayCheck = cpayrepo.findByclReqId_clReqId(clReqId);
        // 확인용, 삭제예정(현제 중복등록이 가능해서 오류 뜸)
        ClassPaymentEntity clpayCheck = cpayrepo.findFirstByclReqId_clReqId(clReqId);

        // 사용할 코드, 삭제 X
        // ScheduleEntity scheduleEntity = schrepo.findByclReqId_clReqId(clReqId);
        // 확인용, 삭제예정(현제 중복등록이 가능해서 오류 뜸)
        ScheduleEntity scheduleEntity = schrepo.findFirstByclReqId_clReqId(clReqId);

        ClassPaymentDTO clPayInfo = ClassPaymentDTO.toDTO(clpayCheck, scheduleEntity);

        // 확인
        // System.out.println(clPayInfo);

        mav.addObject("paymentInfo", clPayInfo);
        mav.setViewName("parent/myclreqView");

        return mav;
    }

    // 신청 마감 기능 추가
    // 특정 선생님의 신청 마감 요일을 DB에서 조회하여 반환했슴둥!
    public List<String> getClosedDaysForTeacher(String tId) {
        List<String> closedDays = new ArrayList<>();
        String[] weekDays = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"};

        for (String day : weekDays) {
            int count = crRepo.countBytId_tIdAndClReqDayAndClReqStatus(tId, day, "결제 완료");
            if (count >= 4) {  // 결제완료가 4명 이상이면 해당 요일은 신청 마감
                closedDays.add(day);
            }
        }
        return closedDays; // 마감된 요일 리스트 반환
    }
}




