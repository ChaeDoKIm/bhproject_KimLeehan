package com.kimleehan.bhproject.vrp.dao;


import com.kimleehan.bhproject.vrp.entity.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface NodeRepository extends JpaRepository<NodeEntity, Long>, Serializable {

    Optional<NodeEntity> findByXAndY(Double x, Double y);
}
