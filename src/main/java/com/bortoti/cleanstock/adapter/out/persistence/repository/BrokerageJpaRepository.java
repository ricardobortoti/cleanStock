package com.bortoti.cleanstock.adapter.out.persistence.repository;

import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageJpaEntity;
import com.bortoti.cleanstock.application.domain.Brokerage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrokerageJpaRepository extends JpaRepository<BrokerageJpaEntity, UUID> {
}
