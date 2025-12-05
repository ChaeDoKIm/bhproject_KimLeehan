package com.kimleehan.bhproject.admin.dao;



import com.kimleehan.bhproject.admin.dto.ClassPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassPaymentRepository extends JpaRepository <ClassPaymentEntity,Long> {

    ClassPaymentEntity findByclReqId_clReqId(Long clReqId);

    // 확인용, 삭제 예정
    ClassPaymentEntity findFirstByclReqId_clReqId(Long clReqId);
}
