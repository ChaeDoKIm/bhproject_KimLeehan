package com.kimleehan.bhproject.user.parent.dao;

import com.kimleehan.bhproject.user.parent.dto.ClassRequestEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRequestRepository extends JpaRepository<ClassRequestEntity, Long> {

    List<ClassRequestEntity> findAllBytId_tId(String tId);

    Optional<ClassRequestEntity> findFirstBytId_tId(String tId);


    // tId를 기준으로 CLREQID 순서대로 가져오기
    List<ClassRequestEntity> findAllBytId_tIdOrderByClReqIdAsc(String tId);

//    // TeacherInfoEntity의 tId와 clReqDay 필드 기반으로 검색
//    List<ClassRequestEntity> findBytId_tIdAndClReqDay(String tId, String clReqDay);

    Optional<ClassRequestEntity> findByclReqId(Long clReqId);

    Optional<ClassRequestEntity> findFirstBysId_sId(Long sId);

    List<ClassRequestEntity> findAllBypId_pId(String pId);

    @Modifying
    @Transactional
    @Query("UPDATE ClassRequestEntity clr SET clr.clReqStatus = :status WHERE clr.clReqId = :clReqId")
    void updateClReqStatus(@Param("clReqId") Long clReqId, @Param("status") String status);


    // 신청마감 기능 추가
    // 특정 선생님의 특정 요일에 '결제 완료' 상태인 신청 개수를 반환
    int countBytId_tIdAndClReqDayAndClReqStatus(String tId, String clReqDay, String clReqStatus);

    // 특정 선생님의 마감된 요일 목록을 조회하는 JPQL 쿼리 (4명 이상일 경우)
    @Query("SELECT cr.clReqDay FROM ClassRequestEntity cr " +
            "WHERE cr.tId.tId = :tId AND cr.clReqStatus = '결제 완료' " +
            "GROUP BY cr.clReqDay " +
            "HAVING COUNT(cr) >= 4")
    List<String> findClosedDaysByTeacher(@Param("tId") String tId);
}
