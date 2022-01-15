package com.bortoti.cleanstock.adapter.out.persistence;

import com.bortoti.cleanstock.adapter.exception.AssetNotFoundException;
import com.bortoti.cleanstock.adapter.exception.BrokerageNotFoundException;
import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.repository.AssetJpaRepository;
import com.bortoti.cleanstock.application.domain.Brokerage;
import com.bortoti.cleanstock.application.domain.BrokerageItem;
import com.bortoti.cleanstock.application.domain.enums.OperationType;
import com.bortoti.cleanstock.testcontainers.MysqlTestContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
class BrokeragePersistenceAdapterTest {

    @Autowired
    BrokeragePersistenceAdapter brokeragePersistenceAdapter;
    @Autowired
    AssetJpaRepository assetJpaRepository;

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

    Brokerage whenHavingOneItemInBrokerage(String ticker){
        List<BrokerageItem> brokerageItems = new ArrayList<>();

        brokerageItems.add(new BrokerageItem(ticker,
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(10),
                OperationType.BUY ));

        return new Brokerage(BigDecimal.valueOf(10),
                LocalDate.now(),
                brokerageItems);
    }

    @Test
    void saveAndLoadBrokerage() throws AssetNotFoundException, BrokerageNotFoundException {
        whenExistsOneAsset("TICK3");
        Brokerage brokerage = whenHavingOneItemInBrokerage("TICK3");

        var persistedBrokerage = brokeragePersistenceAdapter.saveBrokerage(brokerage);
        var loadedBrokerage = brokeragePersistenceAdapter.loadBrokerage(persistedBrokerage.getBrokerageId());

        assertEquals(persistedBrokerage.getBrokerageId(), loadedBrokerage.getBrokerageId());
        assertEquals(persistedBrokerage.getNetBrokerageValue(), loadedBrokerage.getNetBrokerageValue());
        assertEquals(persistedBrokerage.getTotalCosts(), loadedBrokerage.getTotalCosts());
        assertEquals(persistedBrokerage.getTotalOperationsValue(), loadedBrokerage.getTotalOperationsValue());
        assertEquals(persistedBrokerage.getDate(), loadedBrokerage.getDate());
        assertEquals(persistedBrokerage.getItemCount(), loadedBrokerage.getItemCount());
    }

    @Test
    void whenNoAssetExistsMustThrowAssetNotFoundException() {
        Brokerage brokerage = whenHavingOneItemInBrokerage("XNOTF3");

        assertThrows(AssetNotFoundException.class, () -> brokeragePersistenceAdapter.saveBrokerage(brokerage));
    }
}