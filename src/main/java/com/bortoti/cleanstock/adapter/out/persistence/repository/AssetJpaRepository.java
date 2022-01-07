package com.bortoti.cleanstock.adapter.out.persistence.repository;

import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AssetJpaRepository extends JpaRepository<AssetJpaEntity, UUID> {
    Optional<AssetJpaEntity> findByTicker(String ticker);
}
