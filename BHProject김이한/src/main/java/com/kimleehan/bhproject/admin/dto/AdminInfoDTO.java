package com.kimleehan.bhproject.admin.dto;

import lombok.Data;

@Data
public class AdminInfoDTO {

    private String aId;     // 관리자 id
    private String aPass;   // 관리자 비밀번호

    public static AdminInfoDTO toDTO (AdminInfoEntity entity) {
        AdminInfoDTO dto = new AdminInfoDTO();

        dto.setAId(entity.getAId());
        dto.setAPass(entity.getAPass());

        return dto;
    }
}
