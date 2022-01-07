package com.bortoti.cleanstock.adapter.out.persistence.repository;

import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class AssetJpaRepositoryTest {
    @Autowired
    AssetJpaRepository assetJpaRepository;

    @Test
    void persistSmokeTest() {
        for (int i = 0; i<5; i++) {
            AssetJpaEntity assetJpaEntity =
                    new AssetJpaEntity(
                            UUID.randomUUID(),
                            "TICK%s".formatted(i)
                    );
            assetJpaRepository.save(assetJpaEntity);
        }


        var all = assetJpaRepository.findAll();

        assertEquals(5, all.stream().count());
    }

}