package com.kimleehan.bhproject.admin.dao;

import com.kimleehan.bhproject.admin.dto.FaqBoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FaqBoardRepository extends JpaRepository<FaqBoardEntity, Long> {
    Optional<FaqBoardEntity> findByqWriter(String qId);

    Optional<FaqBoardEntity> findFirstByqWriter(String loginId);

    List<FaqBoardEntity> findAllByqWriter(String loginId);

    @Modifying
    @Transactional
    @Query("UPDATE FaqBoardEntity fboard SET fboard.qStatus = '답변 완료' WHERE fboard.qId = :qId")
    void updateAnswerStatus(Long qId);
}
