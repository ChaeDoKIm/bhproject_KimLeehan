package com.kimleehan.bhproject.admin.dao;

import com.kimleehan.bhproject.admin.dto.SignupRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignupRequestRepository extends JpaRepository<SignupRequestEntity, Long> {
}
