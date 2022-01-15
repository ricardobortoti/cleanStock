package com.bortoti.cleanstock.adapter.out.persistence.repository;

import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.entity.OperationJpaEntity;
import com.bortoti.cleanstock.application.domain.enums.OperationType;
import com.bortoti.cleanstock.testcontainers.MysqlTestContainer;
import javax.persistence.Query;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class OperationJpaRepositoryTest {
    @Autowired
    OperationJpaRepository operationJpaRepository;
    @Autowired
    AssetJpaRepository assetJpaRepository;

    @BeforeAll
    private static void initDatabaseProperties() {
        mysqlTestContainer.start();
    }

    @ClassRule
    public static MySQLContainer<MysqlTestContainer> mysqlTestContainer = MysqlTestContainer.getInstance();

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

        assertEquals(5, all.size());
    }
}