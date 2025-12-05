package com.kimleehan.bhproject.vrp.dao;


import com.kimleehan.bhproject.vrp.entity.NodeCostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface NodeCostRepository extends JpaRepository<NodeCostEntity, Long>, Serializable {
    Optional<NodeCostEntity> findByStartNodeId(Long startNodeId);

    Optional<NodeCostEntity> findByStartNodeIdAndEndNodeId(Long startNodeId, Long endNodeId);

    NodeCostEntity findFirstByOrderByIdDesc();
}
