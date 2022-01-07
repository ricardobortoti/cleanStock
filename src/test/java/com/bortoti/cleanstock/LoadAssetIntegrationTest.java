package com.bortoti.cleanstock;

import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.repository.AssetJpaRepository;
import com.bortoti.cleanstock.application.domain.Asset;
import com.bortoti.cleanstock.application.port.out.LoadAssetPort;
import com.bortoti.cleanstock.application.service.exception.AssetNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoadAssetIntegrationTest {

    @Autowired
    private LoadAssetPort loadAssetPort;

    @Autowired
    private AssetJpaRepository assetJpaRepository;

    @BeforeAll
    void createAssets() {
        for (int i = 0; i<5; i++) {
            AssetJpaEntity assetJpaEntity =
                    new AssetJpaEntity(
                            UUID.randomUUID(),
                            "TICK%s".formatted(i)
                    );
            assetJpaRepository.save(assetJpaEntity);
        }
    }

    @Test
    void loadAssetIntegrationSuccess() throws AssetNotFoundException {
        for (int i = 0; i<5; i++) {
            Asset asset = loadAssetPort.loadAsset("TICK%s".formatted(i));
            assertEquals("TICK%s".formatted(i), asset.getTicker());
        }
    }

}
