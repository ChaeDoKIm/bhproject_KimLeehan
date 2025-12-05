package com.kimleehan.bhproject.user.parent.service;

import com.kimleehan.bhproject.admin.dao.ClassPaymentRepository;
import com.kimleehan.bhproject.admin.dto.ClassPaymentEntity;
import com.kimleehan.bhproject.user.parent.dao.ClassRequestRepository;
import com.kimleehan.bhproject.user.parent.dao.ParentInfoRepository;
import com.kimleehan.bhproject.user.parent.dao.StudentInfoRepository;
import com.kimleehan.bhproject.user.parent.dto.*;
import com.kimleehan.bhproject.user.teacher.dao.ClassProgressRepository;
import com.kimleehan.bhproject.user.teacher.dao.TeacherCommentRepository;
import com.kimleehan.bhproject.user.teacher.dto.ClassProgressEntity;
import com.kimleehan.bhproject.user.teacher.dto.TeacherCommentDTO;
import com.kimleehan.bhproject.user.teacher.dto.TeacherCommentEntity;
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
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParentInfoService {

    // 학부모 repository
    private final ParentInfoRepository prepo;

    // 학생/자녀 repository
    private final StudentInfoRepository srepo;

    // 신청학습 repository
    private final ClassRequestRepository clreqrepo;

    // 결제 정보 repository
    private final ClassPaymentRepository clpayrepo;

    // 진행학습 repository
    private final ClassProgressRepository cprepo;

    // 선생님 코멘트 repository
    private final TeacherCommentRepository tcrepo;


    // 메일 인증
    private final JavaMailSender mailSender;

    private ModelAndView mav;

    // 프로필 사진 저장 경로 (혹시 모르니까)
    Path pathProfile = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/profile");

    // 암호화
    private BCryptPasswordEncoder pwEnc = new BCryptPasswordEncoder();

    // 로그인 session
    private final HttpSession session;


    // 아이디 존재여부 확인
    public String idCheck(String pId) {
        String result = "";
        Optional<ParentInfoEntity> entity = prepo.findById(pId);

        if (entity.isPresent()) {
            result = "NO";
        } else {
            result = "OK";
        }
        return result;
    }

    // 이메일 인증
    public String emailCheck(String pEmail) {
        String uuid = null;

        // 인증번호
        uuid = UUID.randomUUID().toString().substring(0, 8);

        // 이메일 발송
        MimeMessage mail = mailSender.createMimeMessage();

        String message = "<h2>안녕하세요. 인천일보 아카데미 입니다.</h2>"
                + "<p>인증번호는 <b>" + uuid + " 입니다.</p>";

        try {
            mail.setSubject("인천일보 아카데미 인증번호");
            mail.setText(message, "UTF-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(pEmail));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return uuid;
    }

    // 학부모 회원가입
    public ModelAndView pJoin(ParentInfoDTO parent) {
        System.out.println("[2] controller → service || parent : " + parent);
        mav = new ModelAndView();

        // (2) 비밀번호 암호화
        parent.setPPass(pwEnc.encode(parent.getPPass()));           // 멤버에 있던 원래 비번 불러와서 encode 한 다음 다시 저장
        System.out.println("암호화 이후 parent : " + parent);

        // (3) dto(ParentInfoDTO) → entity(ParentInfoEntity)
        ParentInfoEntity entity = ParentInfoEntity.toEntity(parent);    // member 에 들어간 DTO 내용이 entity 로 바뀜

        // (4) db 저장
        try {
            prepo.save(entity);
            mav.setViewName("redirect:/main");                 // index 페이지로 돌아가기
        } catch (Exception e) {
            mav.setViewName("redirect:/pJoinForm");
            throw new RuntimeException(e);
        }
        return mav;

    }

    // 학부모 로그인
    public ModelAndView pLogin(ParentInfoDTO parent) {

        System.out.println("[2] controller → service || parent : " + parent);
        mav = new ModelAndView();

        Optional<ParentInfoEntity> entity = prepo.findById(parent.getPId());
        if (entity.isPresent()) {

            if (pwEnc.matches(parent.getPPass(), entity.get().getPPass())) {     // 입력한 비번과 인코딩된 비번 비교
                ParentInfoDTO login = ParentInfoDTO.toDTO(entity.get());         // entity에 저장된 데이터를 가져와서 dto로 변환, DTO타입의 login에 저장
                session.setAttribute("parentLoginId", login.getPId());
                mav.setViewName("redirect:/main");
            } else {
                System.out.println("비밀번호가 틀렸습니다.");
                mav.setViewName("redirect:/pLoginForm");
            }
        } else {
            System.out.println("아이디가 존재하지 않습니다.");
            mav.setViewName("redirect:/pLoginForm");
        }
        return mav;
    }

    // 학부모 내정보 보기 (학생 정보 및 진행학습)
    @Transactional
    public ModelAndView pView(String pId) {
        System.out.println("[2] controller → service || pId : " + pId);
        ModelAndView mav = new ModelAndView();

        List<StudentInfoEntity> entityList;

        try {
            entityList = srepo.findAllBypId_pId(pId);  // 학생 목록 불러오기
        } catch (Exception e) {
            System.err.println("Error fetching student list: " + e.getMessage());
            mav.setViewName("/"); // 메인으로 돌아가기
            return mav;
        }

        List<StudentClassInfoDTO> studentList = new ArrayList<>();

        for (StudentInfoEntity studentEntity : entityList) {
            StudentClassInfoDTO student = new StudentClassInfoDTO();
            student.setSId(studentEntity.getSId());
            student.setSName(studentEntity.getSName());
            student.setSGrade(studentEntity.getSGrade());

            try {
                // 학습 진행 정보 불러오기
                ClassProgressEntity classProgress = cprepo.findFirstBysId_sId(studentEntity.getSId()).orElse(null);

                if (classProgress != null) {
                    student.setClProgId(classProgress.getClProgId());
                    student.setClProgBook(classProgress.getClProgBook());
                    student.setClProgSubject(classProgress.getClProgSubject());
                    student.setClProgStatus(classProgress.getClProgStatus());
                    student.setClProgPercent(classProgress.getClProgPercent());
                    student.setClProgStartDate(classProgress.getClProgStartDate());
                    student.setClProgEndDate(classProgress.getClProgEndDate());
                    student.setTName(classProgress.getTId().getTName());
                    student.setTId((classProgress.getTId().getTId()));


                    // 최신 선생님 코멘트 불러오기
                    try {
                        tcrepo.findFirstByclProgId_clProgId(classProgress.getClProgId(), Sort.by(Sort.Direction.DESC, "cDate"))
                                .ifPresent(teacherComment -> {
                                    student.setCProgress(teacherComment.getCProgress());
                                    student.setCPage(teacherComment.getCPage());
                                    student.setCClassContents(teacherComment.getCClassContents());
                                    student.setCContents(teacherComment.getCContents());
                                });
                    } catch (Exception e) {
                        System.err.println("코멘트 불러오는 중 오류 발생: " + e.getMessage());
                    }

                } else {
                    // 진행 학습 없을 때 신청 학습 확인/불러오기
                    try {
                        ClassRequestEntity classRequest = clreqrepo.findFirstBysId_sId(studentEntity.getSId()).orElse(null);
                        if (classRequest != null) {
                            ClassPaymentEntity checkPayment = clpayrepo.findFirstByclReqId_clReqId(classRequest.getClReqId());
                            if (checkPayment != null) {
                                student.setClProgStatus(checkPayment.getClStatus());
                            } else {
                                student.setClProgStatus("신청 확인 중");
                            }
                        } else {
                            student.setClProgStatus("현재 진행 중인 학습이 없습니다");
                        }
                    } catch (Exception e) {
                        System.err.println("학습신청 불러오는 중 오류 발생: " + e.getMessage());
                        student.setClProgStatus("현재 진행 중인 학습이 없습니다");
                    }
                }

            } catch (Exception e) {
                System.err.println("Error processing student data: " + e.getMessage());
                student.setClProgStatus("데이터 오류 발생");
            }

            studentList.add(student);
        }

        mav.addObject("studentList", studentList);
        mav.setViewName("parent/myChild");

        return mav;
    }

    // 자녀/학생 등록
    public boolean sAddChild(StudentInfoDTO student) {
        System.out.println("[2] controller → service || student : " + student);

        try {
//            // 학부모 아이디 불러오기
//            String parentId = student.getPId();

            // DTO → Entity 변환 후 DB 저장
            StudentInfoEntity entity = StudentInfoEntity.toEntity(student);
            srepo.save(entity);

            return true; // 성공 시 true 반환

        } catch (Exception e) {
            e.printStackTrace(); // 에러 로그 출력
            return false; // 실패 시 false 반환
        }
    }

    // 내 자녀 이전 코멘트 보기
    public List<TeacherCommentDTO> showComments(Long clProgId) {
        List<TeacherCommentDTO> commentList = new ArrayList<>();
        List<TeacherCommentEntity> entityList = tcrepo
                .findAllByclProgId_clProgId(clProgId, Sort.by(Sort.Direction.DESC, "cDate"));

        for (TeacherCommentEntity entity : entityList) {
            TeacherCommentDTO comment = TeacherCommentDTO.toDTO(entity);
            commentList.add(comment);
        }
        return commentList;
    }

    @Transactional
    public ModelAndView myInfoP(String pId) {
        System.out.println("[2] controller → service || pId : " + pId);
        mav = new ModelAndView();

        Optional<ParentInfoEntity> entity = prepo.findById(pId);
        List<StudentInfoEntity> studentList;
        if (entity.isPresent()) {
            ParentInfoDTO parent = ParentInfoDTO.toDTO(entity.get());
            studentList = srepo.findAllBypId_pId(pId);
            mav.addObject("parent", parent);
            mav.addObject("studentList", studentList);
        }

        mav.setViewName("parent/myInfoP");

        return mav;
    }


}
