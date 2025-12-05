package com.kimleehan.bhproject.user.parent.dto;

import lombok.Data;

@Data
public class ParentInfoDTO {

    private String pId;         // 학부모 id
    private String pPass;       // 학부모
    private String pName;       // 학부모 성함
    private String pPhone;      // 학부모 연락처
     private String pAddress;    // 학부모 주소
    private String pEmail;      // 학부모 이메일

    public static ParentInfoDTO toDTO(ParentInfoEntity entity) {
        ParentInfoDTO dto = new ParentInfoDTO();

        dto.setPId(entity.getPId());
        dto.setPPass(entity.getPPass());
        dto.setPName(entity.getPName());
        dto.setPPhone(entity.getPPhone());
         dto.setPAddress(entity.getPAddress());
        dto.setPEmail(entity.getPEmail());

        return dto;
    }

}
