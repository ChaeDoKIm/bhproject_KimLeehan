package com.kimleehan.bhproject.admin.dao;

import com.kimleehan.bhproject.admin.dto.ResumeRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRequestRepository extends JpaRepository<ResumeRequestEntity, Long> {
}
