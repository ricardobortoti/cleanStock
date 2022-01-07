package com.bortoti.cleanstock.adapter.out.persistence.repository;

import com.bortoti.cleanstock.adapter.out.persistence.entity.OperationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OperationJpaRepository extends JpaRepository<OperationJpaEntity, UUID> {
}
