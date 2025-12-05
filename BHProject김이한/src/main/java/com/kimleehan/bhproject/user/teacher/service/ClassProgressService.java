package com.kimleehan.bhproject.user.teacher.service;


import com.kimleehan.bhproject.user.parent.dao.StudentInfoRepository;
import com.kimleehan.bhproject.user.teacher.dao.ClassProgressRepository;


import com.kimleehan.bhproject.user.teacher.dao.TeacherInfoRepository;
import com.kimleehan.bhproject.user.teacher.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ClassProgressService {

    private final ClassProgressRepository cprepo;
    private final TeacherInfoRepository trepo;
    private final StudentInfoRepository srepo;
    private ModelAndView mav;


    public ModelAndView cpInfo(Long clProgId) {
        System.out.println("[2] controller → service || clProgId : " + clProgId);
        mav = new ModelAndView();

        Optional<ClassProgressEntity> entity = cprepo.findById(clProgId);
        if (entity.isPresent()) {
            ClassProgressDTO progress = ClassProgressDTO.toDTO(entity.get());
            mav.addObject("view", progress);
            mav.setViewName("chat");
            ;
        } else {
            mav.setViewName("redirect:/loginFormP");
        }

        return mav;
    }


    public List<TeacherInfoDTO> findTeachersByStudent(Long sId) {
        // 학생 ID로 진행 중인 학습 데이터 조회
        Optional<ClassProgressEntity> classProgressList = cprepo.findBysId_sId(sId);

        // 학습 데이터에서 선생님 정보를 추출
        return classProgressList.stream()
                .map(classProgress -> new TeacherInfoDTO(
                        classProgress.getTId().getTId(),
                        classProgress.getTId().getTName()
                ))
                .distinct()
                .collect(Collectors.toList());
    }


    // 학생 목록 보기
    @Transactional
    public ModelAndView tStu(String tId) {
        System.out.println("[2] controller → service || tId : " + tId);
        mav = new ModelAndView();

        List<TeacherClassInfoDTO> studentList = new ArrayList<>();

        Optional<ClassProgressEntity> existingClass = cprepo.findFirstBytId_tId(tId);

        if (existingClass.isPresent()) {
            List<ClassProgressEntity> entityStudentList = cprepo.findAllBytId_tId(tId);

            for (ClassProgressEntity entityStudent : entityStudentList) {
                TeacherClassInfoDTO student = TeacherClassInfoDTO.toDTO(entityStudent);
                studentList.add(student);
            }

            mav.addObject("studentList", studentList);
        }

        mav.setViewName("teacher/tChat");
        return mav;
    }


    @Transactional
    public List<Map<String, Object>> getTeacherListByStudent(Long sId) {
        System.out.println("[Service] Fetching teachers for studentId: " + sId);

        // CLASS_PROGRESS 테이블에서 해당 학생 ID로 데이터 검색
        Optional<ClassProgressEntity> classProgressList = cprepo.findBysId_sId(sId);

        if (classProgressList.isEmpty()) {
            System.out.println("[Service] No classes found for studentId: " + sId);
            return new ArrayList<>();
        }

        // 필요한 정보(sId, tId, tName, clProgId)를 맵으로 변환
        List<Map<String, Object>> teacherList = classProgressList.stream().map(classProgress -> {
            Map<String, Object> teacherInfo = new HashMap<>();
            teacherInfo.put("sId", classProgress.getSId().getSId());
            teacherInfo.put("clProgId", classProgress.getClProgId());
            teacherInfo.put("tId", classProgress.getTId().getTId());
            teacherInfo.put("tName", classProgress.getTId().getTName());
            return teacherInfo;
        }).collect(Collectors.toList());

        return teacherList;
    }



}