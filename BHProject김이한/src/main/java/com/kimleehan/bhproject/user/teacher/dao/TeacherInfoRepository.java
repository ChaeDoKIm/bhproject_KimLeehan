package com.kimleehan.bhproject.user.teacher.dao;

import com.kimleehan.bhproject.user.teacher.dto.TeacherInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherInfoRepository extends JpaRepository<TeacherInfoEntity, String> {
    List<TeacherInfoEntity> findBytArea(String address);
}
