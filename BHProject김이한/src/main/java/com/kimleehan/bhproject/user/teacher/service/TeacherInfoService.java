package com.kimleehan.bhproject.user.teacher.service;

import com.kimleehan.bhproject.user.parent.dao.StudentInfoRepository;
import com.kimleehan.bhproject.user.teacher.dao.ClassProgressRepository;
import com.kimleehan.bhproject.user.teacher.dao.ScheduleRepository;
import com.kimleehan.bhproject.user.teacher.dao.TeacherCommentRepository;
import com.kimleehan.bhproject.user.teacher.dao.TeacherInfoRepository;
import com.kimleehan.bhproject.user.teacher.dto.*;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
// 서비스 클래스 상단에
import java.nio.file.Files;


@Service
@RequiredArgsConstructor
@Transactional
public class TeacherInfoService {

    private final TeacherInfoRepository trepo;

    private final ClassProgressRepository cprepo;

    private final TeacherCommentRepository tcrepo;

    private final ScheduleRepository srepo;

    private final StudentInfoRepository sturepo;

    // 메일 인증
    private final JavaMailSender mailSender;
    private final ScheduleRepository scheduleRepository;

    private BCryptPasswordEncoder pwEnc = new BCryptPasswordEncoder();

    // 로그인 session
    private final HttpSession session;

    private ModelAndView mav;

//    // 프로필 사진 저장 경로
//    Path pathProfile = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");
//
//    public ModelAndView addT(TeacherInfoDTO teacher) {
//
//        System.out.println("[2] controller → service || teacher : " + teacher);
//        mav = new ModelAndView();
//
//        // (1) 파일 업로드
//        MultipartFile tProfile = teacher.getTProfile();
//
//        if (!tProfile.isEmpty()) {
//            String uuid = UUID.randomUUID().toString().substring(0, 8);
//            String fileName = tProfile.getOriginalFilename();
//            String tProfileName = uuid + "_" + fileName;
//
//            teacher.setTProfileName(tProfileName);
//
//            String savePath = pathProfile.toString() + "\\" + tProfileName;
//            System.out.println("savePath : " + savePath); // 확인용(나중에 지우기)
//
//            try {
//                tProfile.transferTo(new File(savePath));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }


    // 프로젝트 내 정적 리소스(profile) 폴더 경로
    private final Path pathProfile = Paths.get(
            System.getProperty("user.dir"),
            "src", "main", "resources", "static", "profile"
    );

    public ModelAndView addT(TeacherInfoDTO teacher) {

        System.out.println("[2] controller → service || teacher : " + teacher);
        mav = new ModelAndView();

        MultipartFile tProfile = teacher.getTProfile();

        if (tProfile != null && !tProfile.isEmpty()) {
            try {
                // 📂 1) 폴더가 없으면 자동 생성
                if (Files.notExists(pathProfile)) {
                    Files.createDirectories(pathProfile);
                    System.out.println("프로필 폴더 생성됨: " + pathProfile.toString());
                }

                // 📂 2) 파일 이름 생성
                String uuid = UUID.randomUUID().toString().substring(0, 8);
                String originalFilename = tProfile.getOriginalFilename();
                String tProfileName = uuid + "_" + originalFilename;

                teacher.setTProfileName(tProfileName);

                // 📂 3) 최종 저장 경로
                Path savePath = pathProfile.resolve(tProfileName);
                System.out.println("savePath : " + savePath);

                // 📂 4) 실제 파일 저장
                tProfile.transferTo(savePath.toFile());

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("프로필 이미지 저장 중 오류 발생", e);
            }
        }
        // (2) 비밀번호 암호화
        teacher.setTPass(pwEnc.encode(teacher.getTPass()));
        System.out.println("암호화 이후 teacher : " + teacher);

        TeacherInfoEntity entity = TeacherInfoEntity.toEntity(teacher);

        try {
            trepo.save(entity);
            mav.setViewName("admin/main");
        } catch (Exception e) {
            mav.setViewName("redirect:/addTForm");
            throw new RuntimeException(e);
        }
        return mav;
    }

    // 선생님 로그인
    //TODO: 로그인 실패시 예외처리
    public ModelAndView tLogin(TeacherInfoDTO teacher) {

        System.out.println("[2] controller → service || teacher : " + teacher);
        mav = new ModelAndView();

        // (1) 아이디 존재여부 확인
        Optional<TeacherInfoEntity> entity = trepo.findById(teacher.getTId());
        if (entity.isPresent()) {
            // (2) 해당 아이디의 암호화 된 비밀번호와 로그인 페이지에서 입력한 비밀번호가 일치하는지 확인
            // DB에 저장된 비밀번호 : entity.get().getMPw() (암호화 됨)
            // 로그인 창에서 입력한 비밀번호 : member.getMPw()
            if (pwEnc.matches(teacher.getTPass(), entity.get().getTPass())) {     // 입력한 비번과 인코딩된 비번 비교
                // (3) entity → dto
                TeacherInfoDTO login = TeacherInfoDTO.toDTO(entity.get());            // entity에 저장된 데이터를 가져와서 dto로 변환, DTO타입의 login에 저장
                session.setAttribute("teacherLoginId", login.getTId());
                mav.setViewName("teacher/Tmain");
            } else {
                mav.setViewName("redirect:/tLoginForm");
                System.out.println("비밀번호가 틀렸습니다.");
            }
        } else {
            mav.setViewName("redirect:/tLoginForm");
            System.out.println("아이디가 존재하지 않습니다.");
        }
        return mav;
    }
//
//    // 선생님 일정 보기
//    public ModelAndView tMySchedule(String tId) {
//        System.out.println("[2] controller → service || tId : " + tId);
//        ModelAndView mav = new ModelAndView();
//
//        // 데이터 조회
//        List<ScheduleEntity> schedules = srepo.findBytId_tId(tId);
//        if (!schedules.isEmpty()) {
//
//            List<SchedulesDTO> dtoList = schedules.stream()
//                    .map(schedule -> {
//                        SchedulesDTO dto = SchedulesDTO.toDTO(schedule);
//                        String studentName = schedule.getSId().getSName(); // Implement this method to fetch the student name
//                        dto.setSName(studentName);
//                        return dto;
//                    })
//                    .sorted(Comparator.comparing(SchedulesDTO::getPlanDay)
//                            .thenComparing(SchedulesDTO::getPlanId)) // 요일 및 planId로 정렬
//                    .collect(Collectors.toList());
//
//
//            // ScheduleEntity를 SchedulesDTO로 변환
////            List<SchedulesDTO> dtoList = schedules.stream()
////                    .map(SchedulesDTO::toDTO)
////                    .sorted(Comparator.comparing(SchedulesDTO::getPlanDay)
////                            .thenComparing(SchedulesDTO::getPlanId)) // 요일 및 planId로 정렬
////                    .collect(Collectors.toList());
//
//            String tName = dtoList.get(0).getTName();
//
//            // 시간별로 데이터를 매핑
//            Map<String, List<SchedulesDTO>> scheduleMap = new LinkedHashMap<>();
//            scheduleMap.put("13:00 ~ 14:30", new ArrayList<>());
//            scheduleMap.put("15:00 ~ 16:30", new ArrayList<>());
//            scheduleMap.put("17:00 ~ 18:30", new ArrayList<>());
//            scheduleMap.put("21:30 ~ 23:00", new ArrayList<>());
//
//            // 정렬된 데이터를 시간대별로 분배
//            int timeSlotIndex = 0;
//            for (SchedulesDTO schedule : dtoList) {
//                List<String> timeSlots = new ArrayList<>(scheduleMap.keySet());
//                scheduleMap.get(timeSlots.get(timeSlotIndex % timeSlots.size())).add(schedule);
//                timeSlotIndex++;
//            }
//
//            mav.addObject("view", scheduleMap);
//            mav.addObject("tName", tName);
//            mav.setViewName("teacher/mySchedule");
//        } else {
//            mav.setViewName("teacher/mySchedule");
//        }
//        return mav;
//    }

    // 선생님 코멘트 불러오기
    @Transactional
    public List<TeacherCommentDTO> tCommentList(Long clProgId) {
        System.out.println("[2] controller → service || clProgId : " + clProgId);

        List<TeacherCommentDTO> commentList = new ArrayList<>();

        // 코멘트 존재여부 검색
        Optional<TeacherCommentEntity> existingClass = tcrepo.findFirstByclProgId_clProgId(clProgId, Sort.by(Sort.Direction.DESC, "cDate"));

        if (existingClass.isPresent()) {
            // 존재한다면 모두 검색해서 리스트에 담기
            List<TeacherCommentEntity> entityCommentList = tcrepo.findAllByclProgId_clProgId(clProgId, Sort.by(Sort.Direction.DESC, "cDate"));

            // DTO로 변환
            for (TeacherCommentEntity entityComment : entityCommentList) {
                TeacherCommentDTO comment = TeacherCommentDTO.toDTO(entityComment);
                commentList.add(comment);
            }
        }
        return commentList;
    }


    // 학생 목록 보기
    @Transactional
    public ModelAndView tStudents(String tId) {
        System.out.println("[2] controller → service || tId : " + tId);
        mav = new ModelAndView();

        List<TeacherClassInfoDTO> studentList = new ArrayList<>();

        Optional<ClassProgressEntity> existingClass = cprepo.findFirstBytId_tId(tId);

        if (existingClass.isPresent()) {
            List<ClassProgressEntity> entityStudentList = cprepo.findAllBytId_tId(tId);

            for (ClassProgressEntity entityStudent : entityStudentList) {
                TeacherClassInfoDTO student = TeacherClassInfoDTO.toDTO(entityStudent);
                int countWeeks = tcrepo.countAllByclProgId_clProgId(entityStudent.getClProgId());
                student.setCProgress(countWeeks);
                studentList.add(student);
            }

            mav.addObject("studentList", studentList);
        }

        mav.setViewName("teacher/myStudents");
        return mav;
    }

    // 선생님 개인정보 보기
    public ModelAndView tMyInfo(String tId) {
        System.out.println("[2] controller → service || tId : " + tId);
        mav = new ModelAndView();

        Optional<TeacherInfoEntity> entity = trepo.findById(tId);
        if (entity.isPresent()) {
            TeacherInfoDTO teacher = TeacherInfoDTO.toDTO(entity.get());
            mav.addObject("view", teacher);
            mav.setViewName("teacher/myInfoT");
            ;
        } else {
            mav.setViewName("redirect:/tLoginForm");
        }
        return mav;
    }

    // 코멘트 작성
    public ModelAndView addComment(TeacherCommentDTO teacherComment, Long sId, String clProgBook) {

        System.out.println("[2] controller → service || teacherComment : " + teacherComment);
        mav = new ModelAndView();

        String tId = session.getAttribute("teacherLoginId").toString();

        try {
            TeacherCommentEntity teacherCommentEntity = TeacherCommentEntity.toEntity(teacherComment);
            tcrepo.save(teacherCommentEntity);

            try {
                Optional<ClassProgressEntity> classProgress = cprepo.findFirstBysId_sId(sId);
                if (classProgress.isPresent()) {
                    ClassProgressEntity classProgressEntity = classProgress.get();
                    classProgressEntity.setClProgBook(clProgBook);
                    classProgressEntity.setClProgPercent(classProgressEntity.getClProgPercent() + 8.33);
                    if (classProgressEntity.getClProgPercent() >= 99.0) {
                        classProgressEntity.setClProgPercent(100.0);
                        classProgressEntity.setClProgStatus("학습 종료");
                    }
                    cprepo.save(classProgressEntity);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("학습 진도 정보 수정 중 오류 발생");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("코멘트 저장 중 오류 발생 ");
        }

        mav.setViewName("redirect:/tStudents/" + tId);
        return mav;
    }

    // 일정 저장 기능 TEST 중
    public void save(ScheduleEntity scheduleEntity) {
        srepo.save(scheduleEntity);
    }

    // 선생님 정보 수정
    public ModelAndView modInfoT(TeacherInfoDTO teacher) {
        System.out.println("[2] controller → service || teacher : " + teacher);
        mav = new ModelAndView();

        // 아이디로 선생님 찾기
        TeacherInfoEntity teacherEntity = trepo.findById(teacher.getTId()).orElseThrow(() -> new RuntimeException("Teacher not found"));

        // 빈칸 외에 정보 수정하기 메소드
        updatedInfo(teacher, teacherEntity);

        trepo.save(teacherEntity);

        mav.setViewName("redirect:/tMyInfo/" + teacher.getTId());

        return mav;
    }

    // 수정할 정보 확인
    public void updatedInfo(TeacherInfoDTO source, TeacherInfoEntity target) {
        if (source.getTEmail() != null) {
            target.setTEmail(source.getTEmail());
        }
        if (source.getTPhone() != null) {
            target.setTPhone(source.getTPhone());
        }
        if (source.getTArea() != null) {
            target.setTArea(source.getTArea());
        }
        if (source.getTRestDay() != null) {
            target.setTRestDay(source.getTRestDay());
        }
        if (source.getTPass() != null) {
            target.setTPass(pwEnc.encode(source.getTPass()));
        }
        if (source.getTLevel() != null) {
            target.setTLevel(source.getTLevel());
        }
        if (source.getTGrade() != null) {
            target.setTGrade(source.getTGrade());
        }
        if (source.getTSubject() != null) {
            target.setTSubject(source.getTSubject());
        }
    }

    public String tEmailCheck(String tEmail) {
        String uuid = null;

        // 인증번호
        uuid = UUID.randomUUID().toString().substring(0, 8);

        // 이메일 발송
        MimeMessage mail = mailSender.createMimeMessage();

        String message = "<h2>안녕하세요. 방.학 입니다.</h2>"
                + "<p>선생님의 인증번호는 <b>" + uuid + " 입니다.</p>";

        try {
            mail.setSubject("방.학 인증번호");
            mail.setText(message, "UTF-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(tEmail));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return uuid;

    }

    public ModelAndView manageTList() {
        mav = new ModelAndView();
        List<TeacherInfoEntity> entityList = trepo.findAll();
        List<TeacherInfoDTO> teacherList = new ArrayList<>();

        for (TeacherInfoEntity entity : entityList) {
            TeacherInfoDTO teacher = TeacherInfoDTO.toDTO(entity);
            teacherList.add(teacher);
        }

        mav.addObject("teacherList", teacherList);
        mav.setViewName("admin/tlist");

        return mav;
    }

    // 선생님 일정 보기
    public ModelAndView tMySchedule(String tId) {
        System.out.println("[2] controller → service || tId : " + tId);
        ModelAndView mav = new ModelAndView();

        // 0. 해당 선생님 전체 일정 조회
        List<ScheduleEntity> schedules = srepo.findBytId_tId(tId);

        // ✅ (추가했음 확인 필요) 4명이고, 4명 모두 '결제 완료'가 아니면 일정 표시 안 함
        if (!schedules.isEmpty()) {
            int totalCount = schedules.size();
            long paidCount = schedules.stream()
                    .filter(s -> "결제 완료".equals(s.getPlanStatus()))
                    .count();

            // 조건: 상위 4명이고, 그 4명이 모두 결제 완료일 때만 아래 스케줄표 생성
            if (totalCount != 4 || paidCount != 4) {
                // 그냥 빈 화면만 보여줌 (view 없음)
                mav.setViewName("teacher/mySchedule");
                return mav;
            }
        } else {
            // 일정 자체가 없으면 그냥 템플릿만 반환
            mav.setViewName("teacher/mySchedule");
            return mav;
        }

        // ✅ 여기부터는 "4명 전원 결제 완료"일 때만 도는 기존 로직

        List<SchedulesDTO> dtoList = schedules.stream()
                // 혹시 모를 예외를 막으려고 한 번 더 '결제 완료'만 필터
                .filter(s -> "결제 완료".equals(s.getPlanStatus()))
                .map(schedule -> {
                    SchedulesDTO dto = SchedulesDTO.toDTO(schedule);
                    String studentName = schedule.getSId().getSName();
                    dto.setSName(studentName);
                    return dto;
                })
                .sorted(Comparator.comparing(SchedulesDTO::getPlanDay)
                        .thenComparing(SchedulesDTO::getPlanId)) // 요일 및 planId로 정렬
                .collect(Collectors.toList());

        // 최소 한 건은 있다고 가정 (위에서 4명 체크했으니까)
        String tName = dtoList.get(0).getTName();

        // 시간별로 데이터를 매핑
        Map<String, List<SchedulesDTO>> scheduleMap = new LinkedHashMap<>();
        scheduleMap.put("13:00 ~ 14:30", new ArrayList<>());
        scheduleMap.put("15:00 ~ 16:30", new ArrayList<>());
        scheduleMap.put("17:00 ~ 18:30", new ArrayList<>());
        scheduleMap.put("21:30 ~ 23:00", new ArrayList<>());

        // 정렬된 데이터를 시간대별로 분배
        int timeSlotIndex = 0;
        List<String> timeSlots = new ArrayList<>(scheduleMap.keySet());
        for (SchedulesDTO schedule : dtoList) {
            scheduleMap.get(timeSlots.get(timeSlotIndex % timeSlots.size())).add(schedule);
            timeSlotIndex++;
        }

        mav.addObject("view", scheduleMap);
        mav.addObject("tName", tName);
        mav.setViewName("teacher/mySchedule");
        return mav;
    }

}
