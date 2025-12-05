package com.kimleehan.bhproject.admin.service;

import com.kimleehan.bhproject.admin.dao.AdminInfoRepository;
import com.kimleehan.bhproject.admin.dto.AdminInfoDTO;
import com.kimleehan.bhproject.admin.dto.AdminInfoEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminInfoService {

    private final AdminInfoRepository arepo;

    private final HttpSession session;

    // 관리자 로그인 처리
    public ModelAndView aLogin(AdminInfoDTO admin) {
        System.out.println("[2] controller → service || admin : " + admin);
        ModelAndView mav = new ModelAndView();

        // (1) 아이디 존재여부 확인
        Optional<AdminInfoEntity> entity = arepo.findById(admin.getAId());

        if (entity.isPresent()) {
            // (2) 해당 아이디의 암호화 된 비밀번호와 로그인 페이지에서 입력한 비밀번호가 일치하는지 확인
            // DB에 저장된 비밀번호 : entity.get().getMPw() (암호화 됨)
            // 로그인 창에서 입력한 비밀번호 : member.getMPw()
            if (admin.getAPass().equals(entity.get().getAPass())) {     // 입력한 비번과 인코딩된 비번 비교
                // (3) entity → dto
                AdminInfoDTO login = AdminInfoDTO.toDTO(entity.get());            // entity에 저장된 데이터를 가져와서 dto로 변환, DTO타입의 login에 저장
                session.setAttribute("adminLoginId", login.getAId());

                mav.setViewName("/admin/main");
            } else {
                System.out.println("비밀번호가 틀렸습니다.");
                mav.setViewName("redirect:/adminlogin");
            }
        } else {
            System.out.println("아이디가 존재하지 않습니다.");
            mav.setViewName("redirect:/adminlogin");
        }
        return mav;
    }
}
