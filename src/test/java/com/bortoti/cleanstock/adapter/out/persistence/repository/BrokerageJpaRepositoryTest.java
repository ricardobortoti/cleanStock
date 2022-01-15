package com.bortoti.cleanstock.adapter.out.persistence.repository;

import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageItemJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageJpaEntity;
import com.bortoti.cleanstock.application.domain.Brokerage;
import com.bortoti.cleanstock.application.domain.BrokerageItem;
import com.bortoti.cleanstock.application.domain.enums.OperationType;
import com.bortoti.cleanstock.testcontainers.MysqlTestContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class BrokerageJpaRepositoryTest {
    @Autowired
    BrokerageJpaRepository brokerageJpaRepository;


    @BeforeAll
    private static void initDatabaseProperties() {
        mysqlTestContainer.start();
    }

    @ClassRule
    public static MySQLContainer<MysqlTestContainer> mysqlTestContainer = MysqlTestContainer.getInstance();

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
        assertNotNull(savedEntity);
        assertEquals(savedEntity.getBrokerageId(), recoveredEntity.orElseThrow(null).getBrokerageId());
    }
}