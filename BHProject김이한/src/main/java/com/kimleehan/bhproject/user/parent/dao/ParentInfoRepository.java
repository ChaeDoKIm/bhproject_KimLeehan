package com.kimleehan.bhproject.user.parent.dao;

import com.kimleehan.bhproject.user.parent.dto.ParentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentInfoRepository extends JpaRepository<ParentInfoEntity,String> {

}
