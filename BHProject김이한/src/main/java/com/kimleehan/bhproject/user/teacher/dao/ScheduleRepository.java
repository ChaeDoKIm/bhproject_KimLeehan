package com.kimleehan.bhproject.user.teacher.dao;

import com.kimleehan.bhproject.user.teacher.dto.ScheduleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScheduleRepository extends JpaRepository <ScheduleEntity,Long> {

    // 선생님 한 명의 모든 일정
    List<ScheduleEntity>findBytId_tId(String tId);

    List<ScheduleEntity> findBytId_tIdOrderByPlanIdAsc(String tId);

    ScheduleEntity findByclReqId_clReqId(Long clReqId);




    // ✅ 여기 추가중임
    // 상태까지 같이 거르는 메서드 추가
    //  선생님 ID + 상태로 필터링
    List<ScheduleEntity> findBytId_tIdAndPlanStatus(String tId, String planStatus);

    // (원하면 정렬까지)
    List<ScheduleEntity> findBytId_tIdAndPlanStatusOrderByPlanStartTimeAsc(String tId, String planStatus);


    // (선택) 선생님 일정 중 결제 완료인 것만 count
    long countBytId_tIdAndPlanStatus(String tId, String planStatus);




    // 결제 대기로 상태 변경
    @Transactional
    @Modifying
    @Query("UPDATE ScheduleEntity sch set sch.planStatus = '결제 대기' WHERE sch.planId = :planId")
    void updateScheduleStatus(Long planId);
    
    // 결제 완료로 상태 변경
    @Transactional
    @Modifying
    @Query("UPDATE ScheduleEntity sch set sch.planStatus = '결제 완료' WHERE sch.planId = :planId")
    void updateScheduleStatusComplete(Long planId);

    // 확인용, 삭제 예정
    ScheduleEntity findFirstByclReqId_clReqId(Long clReqId);
}
