package com.bortoti.cleanstock.mapper;

import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.application.domain.Asset;
import org.mapstruct.Mapper;

@Mapper
public interface AssetMapper {
    Asset toDomain(AssetJpaEntity assetJpaEntity);
    AssetJpaEntity toJpaEntity(Asset asset);
}
