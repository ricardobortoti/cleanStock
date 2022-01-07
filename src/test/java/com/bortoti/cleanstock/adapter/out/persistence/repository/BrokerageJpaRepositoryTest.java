package com.bortoti.cleanstock.adapter.out.persistence.repository;

import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageItemJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageJpaEntity;
import com.bortoti.cleanstock.application.domain.Brokerage;
import com.bortoti.cleanstock.application.domain.BrokerageItem;
import com.bortoti.cleanstock.application.domain.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BrokerageJpaRepositoryTest {
    @Autowired
    BrokerageJpaRepository brokerageJpaRepository;

    @Test
    void persistSmokeTest() {
        BrokerageJpaEntity brokerage = new BrokerageJpaEntity(
                null,
                BigDecimal.valueOf(10.22),
                LocalDate.now(),
                null);

        var brokergeItems = new ArrayList<BrokerageItemJpaEntity>();
        for (int i = 0; i < 5; i++) {
            brokergeItems.add(
                    new BrokerageItemJpaEntity(null,
                            "TEST3",
                            BigDecimal.valueOf(2),
                            BigDecimal.valueOf(2),
                            OperationType.BUY,
                            brokerage
                    )
            );
        }

        brokerage.setBrokerageItems(brokergeItems);

        var savedEntity = brokerageJpaRepository.save(brokerage);
        var recoveredEntity = brokerageJpaRepository.findById(savedEntity.getBrokerageId());
        assertTrue(savedEntity != null);
        assertEquals(savedEntity.getBrokerageId(), recoveredEntity.get().getBrokerageId());
    }
}