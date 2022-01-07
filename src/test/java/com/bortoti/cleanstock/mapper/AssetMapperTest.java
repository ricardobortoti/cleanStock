package com.bortoti.cleanstock.mapper;

import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.application.domain.Asset;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AssetMapperTest {
    AssetMapper assetMapper = Mappers.getMapper(AssetMapper.class);

    @Test
    void assetJpaEntityToAsset() {
        AssetJpaEntity assetJpaEntity = new AssetJpaEntity(UUID.randomUUID(), "TEST");
        Asset asset = assetMapper.toDomain(assetJpaEntity);

        assertEquals(asset.getId(), assetJpaEntity.getId());
        assertEquals(asset.getTicker(), assetJpaEntity.getTicker());
    }

    @Test
    void assetToAssetJpaEntity() {
        Asset asset = new Asset(UUID.randomUUID(), "TEST");
        AssetJpaEntity assetJpaEntity = assetMapper.toJpaEntity(asset);

        assertEquals(assetJpaEntity.getId(), asset.getId());
        assertEquals(assetJpaEntity.getTicker(), asset.getTicker());
    }
}