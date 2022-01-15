package com.bortoti.cleanstock.adapter.out.persistence;

import com.bortoti.cleanstock.adapter.exception.AssetNotFoundException;
import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.repository.AssetJpaRepository;
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
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.script.ScriptException;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
class AssetPersistenceAdapterTest {

    @Autowired
    private AssetPersistenceAdapter assetPersistenceAdapter;

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

    @Test
    void loadAsset() throws AssetNotFoundException {
        var randomAsset = RandomStringUtils.randomAlphabetic(3).concat("3").toUpperCase(Locale.ROOT);

        whenExistsOneAsset(randomAsset);

        var asset = assetPersistenceAdapter.loadAsset(randomAsset);

        assertEquals(randomAsset, asset.getTicker());
    }

    @Test
    void loadInexistentAssetMustThrowsAssetNotFoundException() {
        assertThrows(AssetNotFoundException.class, () -> assetPersistenceAdapter.loadAsset("XNOTF3"));
    }
}