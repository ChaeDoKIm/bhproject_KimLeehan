//package com.icia.fproject.admin.service;
//
//import com.icia.fproject.admin.dao.ResumeRequestRepository;
//import com.icia.fproject.admin.dto.ResumeRequestDTO;
//import com.icia.fproject.admin.dto.ResumeRequestEntity;
//import jakarta.mail.Message;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.Resource;
//import org.springframework.data.domain.Sort;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class ResumeService {
//
//    private final ResumeRequestRepository resumeRepo;
//    private final JavaMailSender mailSender;
//
//    private ModelAndView mav;
//
//    // 파일 저장 경로
//    Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/upload/resume");
//
//    // 이메일 인증
//    public String emailCheck(String resEmail) {
//        String uuid = UUID.randomUUID().toString().substring(0, 8);
//
//        MimeMessage mail = mailSender.createMimeMessage();
//        String message = "<h2>안녕하세요. 방학 입니다.</h2>"
//                + "<p>인증번호는 <b>" + uuid + " 입니다.</p>";
//
//        try {
//            mail.setSubject("방학 인증번호");
//            mail.setText(message, "UTF-8", "html");
//            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(resEmail));
//
//            // mailSender.send(mail);
//        } catch (MessagingException e) {
//            throw new RuntimeException("이메일 발송 실패", e);
//        }
//
//        return uuid;
//    }
//
//    // 이력서 저장
//    public ModelAndView submitResume(ResumeRequestDTO resume) {
//        System.out.println("[2] controller → service : " + resume);
//        mav = new ModelAndView();
//
//        // (1) 파일 업로드
//        MultipartFile resFile = resume.getResFile();
//
//        if (!resFile.isEmpty()) {
//            String uuid = UUID.randomUUID().toString().substring(0, 8);
//            String fileName = resFile.getOriginalFilename();
//            String resFileName = uuid + "_" + fileName;
//
//            resume.setResFileName(resFileName);
//
//            String savePath = path + "\\" + resFileName;
//            System.out.println("savePath : " + savePath);
//
//            try {
//                resFile.transferTo(new File(savePath));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//        } else {
//            resume.setResFileName("default.pdf");
//        }
//        // (3) dto(ResumeRequestDTO) → entity(ResumeRequestEntity)
//        ResumeRequestEntity entity = ResumeRequestEntity.toEntity(resume);
//        entity.setResStatus("미확인"); // 기본 상태 설정
//
//
//        // (4) db저장
//        try {
//            resumeRepo.save(entity);
//            mav.setViewName("redirect:/main");
//        } catch (Exception e) {
//            mav.setViewName("redirect:/rwrite");
//            throw new RuntimeException(e);
//        }
//
//        return mav;
//    }
//
//    public ModelAndView list() {
//        System.out.println("[2] controller → service ");
//        mav = new ModelAndView();
//
//        try {
//            // (1) DB에서 지원서 리스트 조회 (Entity 타입)
//            List<ResumeRequestEntity> entityList = resumeRepo.findAll(Sort.by(Sort.Direction.ASC, "resId"));
//
//            // (2) Entity 리스트를 DTO 리스트로 변환
//            List<ResumeRequestDTO> dtoList = entityList.stream()
//                    .map(ResumeRequestDTO::toDTO)
//                    .toList();
//
//            // (3) ModelAndView에 데이터 추가
//            mav.setViewName("resume/list"); // 목록 페이지 경로
//            mav.addObject("resumeList", dtoList);
//        } catch (Exception e) {
//            // 예외 처리
//            mav.setViewName("error");
//            mav.addObject("message", "목록을 불러오는 중 문제가 발생했습니다.");
//            e.printStackTrace();
//        }
//
//        return mav;
//    }
//
//    // 입사지원 상세보기
//    public ModelAndView rView(Long resId) {
//        mav = new ModelAndView();
//        try {
//            // Optional로 데이터 조회
//            Optional<ResumeRequestEntity> entity = resumeRepo.findById(resId);
//            if (entity.isPresent()) {
//                ResumeRequestDTO resume = ResumeRequestDTO.toDTO(entity.get());
//                mav.addObject("resume", resume);
//                mav.setViewName("resume/view");
//            } else {
//                mav.setViewName("redirect:/resume/list");
//                mav.addObject("message", "존재하지 않는 이력서입니다.");
//            }
//        } catch (Exception e) {
//            mav.setViewName("error");
//            mav.addObject("message", "이력서 조회 중 문제가 발생했습니다.");
//            e.printStackTrace();
//        }
//        return mav;
//    }
//    // 이력서 다운로드 기능 추가
//    public Resource downloadResume(String fileName) throws MalformedURLException {
//        Path filePath = path.resolve(fileName).normalize();
//        Resource resource = new UrlResource(filePath.toUri());
//
//        if (!resource.exists()) {
//            throw new RuntimeException("파일을 찾을 수 없습니다: " + fileName);
//        }
//
//        return resource;
//    }
//}

package com.kimleehan.bhproject.admin.service;

import com.kimleehan.bhproject.admin.dao.ResumeRequestRepository;
import com.kimleehan.bhproject.admin.dto.ResumeRequestDTO;
import com.kimleehan.bhproject.admin.dto.ResumeRequestEntity;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRequestRepository resumeRepo;
    private final JavaMailSender mailSender;


    private ModelAndView mav;

    // 파일 저장 경로
    Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/upload/resume");

    // 이메일 인증
    public String emailCheck(String resEmail) {
        String uuid = UUID.randomUUID().toString().substring(0, 8);

        MimeMessage mail = mailSender.createMimeMessage();
        String message = "<h2>안녕하세요. 방학 입니다.</h2>"
                + "<p>인증번호는 <b>" + uuid + " 입니다.</p>";

        try {
            mail.setSubject("방.학 인증번호");
            mail.setText(message, "UTF-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(resEmail));

            // mailSender.send(mail);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 발송 실패", e);
        }

        return uuid;
    }

     // 이력서 저장
//    public ModelAndView submitResume(ResumeRequestDTO resume) {
//        System.out.println("[2] controller → service : " + resume);
//        mav = new ModelAndView();
//
//        // (1) 파일 업로드
//        MultipartFile resFile = resume.getResFile();
//
//        if (!resFile.isEmpty()) {
//            String uuid = UUID.randomUUID().toString().substring(0, 8);
//            String fileName = resFile.getOriginalFilename();
//            String resFileName = uuid + "_" + fileName;
//
//            resume.setResFileName(resFileName);
//
//            String savePath = path + "\\" + resFileName;
//            System.out.println("savePath : " + savePath);
//
//            try {
//                resFile.transferTo(new File(savePath));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//        } else {
//            resume.setResFileName("default.pdf");
//        }
//        // (3) dto(ResumeRequestDTO) → entity(ResumeRequestEntity)
//        ResumeRequestEntity entity = ResumeRequestEntity.toEntity(resume);
//        entity.setResStatus("미확인"); // 기본 상태 설정
//
//
//        // (4) db저장
//        try {
//            resumeRepo.save(entity);
//            mav.setViewName("redirect:/main");
//        } catch (Exception e) {
//            mav.setViewName("redirect:/rwrite");
//            throw new RuntimeException(e);
//        }
//
//        return mav;
//    }

    public ModelAndView list() {
        System.out.println("[2] controller → service ");
        mav = new ModelAndView();

        try {
            // (1) DB에서 지원서 리스트 조회 (Entity 타입)
            List<ResumeRequestEntity> entityList = resumeRepo.findAll(Sort.by(Sort.Direction.ASC, "resId"));

            // (2) Entity 리스트를 DTO 리스트로 변환
            List<ResumeRequestDTO> dtoList = entityList.stream()
                    .map(ResumeRequestDTO::toDTO)
                    .toList();

            // (3) ModelAndView에 데이터 추가
            mav.setViewName("resume/list"); // 목록 페이지 경로
            mav.addObject("resumeList", dtoList);
        } catch (Exception e) {
            // 예외 처리
            mav.setViewName("error");
            mav.addObject("message", "목록을 불러오는 중 문제가 발생했습니다.");
            e.printStackTrace();
        }

        return mav;
    }

    // 입사지원 상세보기
    public ModelAndView rView(Long resId) {
        mav = new ModelAndView();
        try {
            // Optional로 데이터 조회
            Optional<ResumeRequestEntity> entity = resumeRepo.findById(resId);
            if (entity.isPresent()) {
                ResumeRequestDTO resume = ResumeRequestDTO.toDTO(entity.get());
                mav.addObject("resume", resume);
                mav.setViewName("resume/view");
            } else {
                mav.setViewName("redirect:/resume/list");
                mav.addObject("message", "존재하지 않는 이력서입니다.");
            }
        } catch (Exception e) {
            mav.setViewName("error");
            mav.addObject("message", "이력서 조회 중 문제가 발생했습니다.");
            e.printStackTrace();
        }
        return mav;
    }

    // 이력서 다운로드 기능 추가
    public Resource downloadResume(String fileName) throws MalformedURLException {
        Path filePath = path.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("파일을 찾을 수 없습니다: " + fileName);
        }

        return resource;
    }


    // 수정 중 ( 기능 업데이트중)
    // 목록
    @Transactional(readOnly = true)
    public List<ResumeRequestDTO> findAll() {
        return resumeRepo.findAll().stream()
                .map(ResumeRequestDTO::toDTO)
                .toList();
    }

    // 상세
    @Transactional(readOnly = true)
    public ResumeRequestDTO findById(Long resId) {
        ResumeRequestEntity entity = resumeRepo.findById(resId)
                .orElseThrow(() -> new IllegalArgumentException("이력서를 찾을 수 없습니다. id=" + resId));
        return ResumeRequestDTO.toDTO(entity);
    }

    // 상태 변경 (합격 / 대기 / 반려)
    @Transactional
    public void changeStatus(Long resId, String status) {

        // 1) 허용 값 체크 (대기 / 합격 / 반려)
        String normalized = status.trim();

        if (!normalized.equals("대기") &&
                !normalized.equals("합격") &&
                !normalized.equals("반려")) {
            throw new IllegalArgumentException("지원하지 않는 상태값입니다: " + status);
        }

        // 2) 엔티티 조회
        ResumeRequestEntity entity = resumeRepo.findById(resId)
                .orElseThrow(() -> new IllegalArgumentException("이력서를 찾을 수 없습니다. id=" + resId));

        // 3) 상태 변경
        entity.setResStatus(normalized);

        // 4) 저장
        resumeRepo.save(entity);
    }


    // 이력서 저장 기능 수정
   // 이력서 저장
public ModelAndView submitResume(ResumeRequestDTO resume) {
    System.out.println("[2] controller → service : " + resume);
    mav = new ModelAndView();

    // (1) 파일 업로드
    MultipartFile resFile = resume.getResFile();

    if (!resFile.isEmpty()) {

        // 0. 폴더가 없으면 생성
        File dir = path.toFile(); // ★ Path → File 변환
        if (!dir.exists()) {
            boolean created = dir.mkdirs(); // 상위폴더까지 생성
            System.out.println("폴더 생성됨? : " + created);
        }

        String uuid = UUID.randomUUID().toString().substring(0, 8);
        String originalName = resFile.getOriginalFilename();
        String resFileName = uuid + "_" + originalName;

        resume.setResFileName(resFileName);

        // ★ Path 방식으로 파일 경로 만들기
        Path savePath = path.resolve(resFileName); // resume 폴더 아래에 파일명 붙이기
        System.out.println("savePath : " + savePath.toString());

        try {
            // 파일 저장
            resFile.transferTo(savePath.toFile());  // ★ Path → File 변환해서 저장
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 중 오류 발생: " + savePath, e);
        }

    } else {
        // 파일이 없으면 기본값
        resume.setResFileName("default.pdf");
    }

    // (3) DTO → Entity
    ResumeRequestEntity entity = ResumeRequestEntity.toEntity(resume);
    entity.setResStatus("미확인"); // 기본 상태값

    // (4) DB 저장
    try {
        resumeRepo.save(entity);
        mav.setViewName("redirect:/main");
    } catch (Exception e) {
        mav.setViewName("redirect:/rwrite");
        throw new RuntimeException(e);
    }

    return mav;
}



}
