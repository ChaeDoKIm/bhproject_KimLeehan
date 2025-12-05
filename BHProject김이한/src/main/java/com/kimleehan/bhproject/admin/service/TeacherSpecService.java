package com.kimleehan.bhproject.admin.service;

import com.kimleehan.bhproject.admin.dao.TeacherSpecRepository;
import com.kimleehan.bhproject.admin.dto.TeacherSpecDTO;
import com.kimleehan.bhproject.admin.dto.TeacherSpecEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherSpecService {
    private final TeacherSpecRepository tsrepo;

    private ModelAndView mav;

    private HttpSession session;
    // 파일 저장 경로
    Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/upload/spec");


    public ModelAndView addSpec(TeacherSpecDTO spec) {
        System.out.println("[2] controller → service || spec : " + spec);
        mav = new ModelAndView();

        // (1) 파일 업로드
        MultipartFile specFile = spec.getSpecFile();

        if (!specFile.isEmpty()) {
            String uuid = UUID.randomUUID().toString().substring(0, 8);
            String fileName = specFile.getOriginalFilename();
            String specFileName = uuid + "_" + fileName;

            spec.setSpecFileName(specFileName);

            String savePath = path.toString() + "\\" + specFileName;
            System.out.println("파일저장경로 확인 : " + savePath);

            try {
                specFile.transferTo(new File(savePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            spec.setSpecFileName("default.pdf");
        }

        spec.setSpecStatus("미확인");
        TeacherSpecEntity entity = TeacherSpecEntity.toEntity(spec);

        try {
            tsrepo.save(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mav.setViewName("redirect:/tMyInfo/" + spec.getTId());

        return mav;
    }

    // 추가 자격증 목록 불러오기
    public ModelAndView getSpecList() {
        System.out.println("[2] controller → service");
        mav = new ModelAndView();


        List<TeacherSpecDTO> specList = new ArrayList<>();

        List<TeacherSpecEntity> entityList = tsrepo.findAll();

        for (TeacherSpecEntity entity : entityList) {
            TeacherSpecDTO spec = TeacherSpecDTO.toDTO(entity);
            specList.add(spec);
        }

        mav.setViewName("admin/teacherSpecList");
        mav.addObject("specList", specList);

        return mav;

    }

    // 자격증 상세보기 확인
    public ModelAndView tViewSpec(Long tSid) {
        System.out.println("[2] controller → service || tSid : " + tSid);
        mav = new ModelAndView();

        Optional<TeacherSpecEntity> spec = tsrepo.findById(tSid);

        if (spec.isPresent()) {
            TeacherSpecDTO specDTO = TeacherSpecDTO.toDTO(spec.get());
            mav.addObject("spec", specDTO);
            mav.setViewName("admin/teacherSpecView");
        }
        return mav;
    }

    // 확인 상태 수정
    public String updateStatus(Long tSid) {
        String result = null;
        Optional<TeacherSpecEntity> spec = tsrepo.findById(tSid);
        if(spec.isPresent()) {
            TeacherSpecEntity entity = spec.get();
            if(entity.getSpecStatus().equals("미확인")) {
                entity.setSpecStatus("검토중");
                tsrepo.save(entity);
                result = "SUCCESS";
            } else {
                result = "FAIL";
            }
        }
        return result;
    }
}
