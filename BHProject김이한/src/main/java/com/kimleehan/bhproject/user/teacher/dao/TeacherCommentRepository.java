package com.kimleehan.bhproject.user.teacher.dao;

import com.kimleehan.bhproject.user.teacher.dto.TeacherCommentEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherCommentRepository extends JpaRepository<TeacherCommentEntity, Long> {

    Optional<TeacherCommentEntity> findFirstByclProgId_clProgId(Long clProgId, Sort cDate);

    List<TeacherCommentEntity> findAllByclProgId_clProgId(Long clProgId, Sort cDate);

    int countAllByclProgId_clProgId(Long clProgId);
}
