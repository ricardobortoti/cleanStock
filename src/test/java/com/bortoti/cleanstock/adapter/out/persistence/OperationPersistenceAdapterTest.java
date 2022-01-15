package com.bortoti.cleanstock.adapter.out.persistence;

import com.bortoti.cleanstock.adapter.exception.AssetNotFoundException;
import com.bortoti.cleanstock.adapter.exception.OperationNotFoundException;
import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.repository.AssetJpaRepository;
import com.bortoti.cleanstock.application.domain.Asset;
import com.bortoti.cleanstock.application.domain.Operation;
import com.bortoti.cleanstock.testcontainers.MysqlTestContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.script.ScriptException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
class OperationPersistenceAdapterTest {

    @Autowired
    private OperationPersistenceAdapter operationPersistenceAdapter;

    @Autowired
    private AssetJpaRepository assetJpaRepository;

    @BeforeAll
    private static void initDatabaseProperties() {
        mysqlTestContainer.start();
    }

    @ClassRule
    public static MySQLContainer<MysqlTestContainer> mysqlTestContainer = MysqlTestContainer.getInstance();

    void whenExistsOneAsset(String ticker) {
        AssetJpaEntity assetJpaEntity = new AssetJpaEntity(UUID.randomUUID(), ticker);
        assetJpaRepository.save(assetJpaEntity);
    }

    @Test
    void saveAndLoadOperation() throws OperationNotFoundException, AssetNotFoundException {
        var randomAsset = RandomStringUtils.randomAlphabetic(3).concat("3").toUpperCase(Locale.ROOT);

        whenExistsOneAsset(randomAsset);
        Asset asset = Asset.withoutId(randomAsset);

        Operation operation = Operation.createBuy(asset,
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(10),
                LocalDate.now());

        Operation savedOperation = operationPersistenceAdapter.saveOperation(operation);
        Operation expectedOperation = operationPersistenceAdapter.loadOperation(savedOperation.getOperationId());

        assertEquals(expectedOperation.getOperationId(), savedOperation.getOperationId());
        assertEquals(expectedOperation.getDate(), savedOperation.getDate());
        assertEquals(expectedOperation.getQuantity(), savedOperation.getQuantity());
        assertEquals(expectedOperation.getTotalValue(), savedOperation.getTotalValue());
        assertEquals(expectedOperation.getAsset().getId(), savedOperation.getAsset().getId());
        assertEquals(expectedOperation.getAsset().getTicker(), savedOperation.getAsset().getTicker());
        assertEquals(expectedOperation.getType(), savedOperation.getType());
    }
}