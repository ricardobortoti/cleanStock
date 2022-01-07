package com.bortoti.cleanstock.adapter.out.persistence.repository;

import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.entity.OperationJpaEntity;
import com.bortoti.cleanstock.application.domain.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OperationJpaRepositoryTest {
    @Autowired
    OperationJpaRepository operationJpaRepository;
    @Autowired
    AssetJpaRepository assetJpaRepository;

    @Test
    void persistSmokeTest() {
        AssetJpaEntity assetJpaEntity = assetJpaRepository.save(new AssetJpaEntity(UUID.randomUUID(), "TEST3"));

        for (int i = 0; i<5; i++) {

            OperationJpaEntity operationJpaEntity = new OperationJpaEntity(null,
                    assetJpaEntity,
                    BigDecimal.valueOf(10),
                    BigDecimal.valueOf(10),
                    OperationType.BUY,
                    LocalDate.now());

            operationJpaRepository.save(operationJpaEntity);
        }

        var all = operationJpaRepository.findAll();

        assertEquals(5, all.stream().count());
    }
}