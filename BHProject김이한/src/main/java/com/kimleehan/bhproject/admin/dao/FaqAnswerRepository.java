package com.kimleehan.bhproject.admin.dao;

import com.kimleehan.bhproject.admin.dto.FaqAnswerEntity;
import com.kimleehan.bhproject.admin.dto.FaqBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaqAnswerRepository extends JpaRepository<FaqAnswerEntity, Long> {
    List<FaqAnswerEntity> findByqId(FaqBoardEntity qId);
}
