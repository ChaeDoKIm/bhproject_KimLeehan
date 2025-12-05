package com.kimleehan.bhproject.user.teacher.dao;

import com.kimleehan.bhproject.user.teacher.dto.ClassProgressEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassProgressRepository extends JpaRepository<ClassProgressEntity, Long> {
    Optional<ClassProgressEntity> findBysId_sId(Long sId);

    List<ClassProgressEntity> findAllBytId_tId(String tId);

    Optional<ClassProgressEntity> findBytId_tId(String tId);

    Optional<ClassProgressEntity> findFirstBytId_tId(String tId);

    Optional<ClassProgressEntity> findFirstBysId_sId(Long sId);

    @Query("SELECT clProg FROM ClassProgressEntity clProg WHERE clProg.sId.sId = :sId AND clProg.tId.tId = :tId")
    Optional<ClassProgressEntity> findBysIdAndtId(@Param("sId") Long sId, @Param("tId") String tId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ClassProgressEntity clProg WHERE clProg.sId.sId = :sId AND clProg.tId.tId = :tId")
    void deleteBysIdAndtId(@Param("sId") Long sId, @Param("tId") String tId);

}
