package com.kimleehan.bhproject.admin.dto;

import lombok.Data;

import java.time.Instant;
// TODO: 학부모/선생님 문의사항 분리
@Data
public class FaqBoardDTO {

    private Long qId;                   // 문의사항 id
    private String qTitle;              // 문의사항 제목
    private String qWriter;             // 작성자
    private String qPass;               // 확인 위한 비회원 비밀번호
    private String qCategory;           // 선생님인지 학부모인지 구별
    private String qContents;           // 문의사항 내용
    private Instant qDate;              // 작성일
    private String qStatus;              // 확인 상태
//    private String qAnswer;             // 답변 내용
//    private Instant qAnswerDate;        // 답변 작성일

    public static FaqBoardDTO toDTO (FaqBoardEntity entity) {
        FaqBoardDTO dto = new FaqBoardDTO();

        dto.setQId(entity.getQId());
        dto.setQTitle(entity.getQTitle());
        dto.setQWriter(entity.getQWriter());
        dto.setQPass(entity.getQPass());
        dto.setQCategory(entity.getQCategory());
        dto.setQContents(entity.getQContents());
        dto.setQDate(entity.getQDate());
        dto.setQStatus(entity.getQStatus());

        return dto;
    }


}
