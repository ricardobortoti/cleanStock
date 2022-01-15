package com.bortoti.cleanstock.adapter.out.persistence;

import com.bortoti.cleanstock.adapter.out.persistence.repository.AssetJpaRepository;
import com.bortoti.cleanstock.application.domain.Asset;
import com.bortoti.cleanstock.application.port.out.LoadAssetPort;
import com.bortoti.cleanstock.adapter.exception.AssetNotFoundException;
import com.bortoti.cleanstock.mapper.AssetMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AssetPersistenceAdapter implements LoadAssetPort {
    private final AssetJpaRepository assetJpaRepository;
    private final AssetMapper assetMapper = Mappers.getMapper(AssetMapper.class);

    @Override
    public Asset loadAsset(String ticker) throws AssetNotFoundException {
        var assetJpaEntity = assetJpaRepository.findByTicker(ticker).orElseThrow(() -> new AssetNotFoundException("Asset not found"));
        return assetMapper.toDomain(assetJpaEntity);
    }
}
