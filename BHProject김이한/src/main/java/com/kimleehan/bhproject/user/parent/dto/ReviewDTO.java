package com.kimleehan.bhproject.user.parent.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class ReviewDTO {

    private Long revId;         // 후기 ID

    private Instant revDate;    // 후기 작성/등록일

    private Long revRate;       // 후기에 남긴 별점

    private String tId;        // 후기 받은 선생님

    private Long sId;          // 작성자 (학생 ID)


    public static ReviewDTO toDTO(ReviewEntity entity) {
        ReviewDTO dto = new ReviewDTO();

        dto.setRevId(entity.getRevId());
        dto.setRevDate(entity.getRevDate());
        dto.setRevRate(entity.getRevRate());

        dto.setTId(entity.getTId().getTId());
        dto.setSId(entity.getSId().getSId());
        return dto;
    }

    //  엔터티 -> DTO로 변환 메서드 추가
    public static ReviewDTO fromEntity(ReviewEntity entity) {
        ReviewDTO dto = new ReviewDTO();
        dto.setRevId(entity.getRevId());
        dto.setRevDate(entity.getRevDate());
        dto.setRevRate(entity.getRevRate());

        if (entity.getSId() != null) {
            dto.setSId(entity.getSId().getSId());
        }

        if (entity.getTId() != null) {
            dto.setTId(entity.getTId().getTId());
        }

        return dto;
    }

}
