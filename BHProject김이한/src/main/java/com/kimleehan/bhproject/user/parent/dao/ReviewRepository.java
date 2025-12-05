package com.kimleehan.bhproject.user.parent.dao;

import com.kimleehan.bhproject.user.parent.dto.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findBytId_tId(String tId);

    @Query("SELECT c.tId FROM ClassProgressEntity c WHERE c.sId.sId = :sId AND c.clProgStatus = '진행 중'")
    String findtIdBysId(@Param("sId") Long sId);


    @Query("SELECT COUNT(r) FROM ReviewEntity r WHERE r.sId.sId = :sId AND r.tId .tId= :tId")
    int countBysIdAndtId(@Param("sId") Long sId, @Param("tId") String tId);



}
