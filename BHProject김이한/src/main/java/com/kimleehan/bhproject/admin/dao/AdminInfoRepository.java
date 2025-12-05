package com.kimleehan.bhproject.admin.dao;

import com.kimleehan.bhproject.admin.dto.AdminInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminInfoRepository extends JpaRepository<AdminInfoEntity, String> {
}
