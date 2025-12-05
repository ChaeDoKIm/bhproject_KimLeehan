package com.kimleehan.bhproject.user.parent.dao;

import com.kimleehan.bhproject.user.parent.dto.StudentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfoEntity, Long> {

    List<StudentInfoEntity> findAllBypId_pId(String pId);
    Optional<StudentInfoEntity> findBysId(Long sId);


}
