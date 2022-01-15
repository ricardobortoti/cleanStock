package com.bortoti.cleanstock.adapter.out.persistence;

import com.bortoti.cleanstock.adapter.exception.AssetNotFoundException;
import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageItemJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.repository.AssetJpaRepository;
import com.bortoti.cleanstock.adapter.out.persistence.repository.BrokerageJpaRepository;
import com.bortoti.cleanstock.application.domain.Brokerage;
import com.bortoti.cleanstock.application.domain.BrokerageItem;
import com.bortoti.cleanstock.application.port.out.LoadBrokeragePort;
import com.bortoti.cleanstock.application.port.out.SaveBrokeragePort;
import com.bortoti.cleanstock.adapter.exception.BrokerageNotFoundException;
import com.bortoti.cleanstock.mapper.BrokerageMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Component
@Transactional
public class BrokeragePersistenceAdapter implements SaveBrokeragePort, LoadBrokeragePort {
    private final BrokerageJpaRepository brokerageJpaRepository;
    private final AssetJpaRepository assetJpaRepository;
    private final BrokerageMapper brokerageMapper = Mappers.getMapper(BrokerageMapper.class);

    @Override
    public Brokerage saveBrokerage(Brokerage brokerage) throws AssetNotFoundException {
        var brokerageJpaEntity = brokerageMapper.toJpaEntity(brokerage);


        for (BrokerageItemJpaEntity brokerageItemJpaEntity:brokerageJpaEntity.getBrokerageItems()) {
            var assetJpaEntity = assetJpaRepository
                    .findByTicker(brokerageItemJpaEntity.getAssetTicker()).orElseThrow(AssetNotFoundException::new);

            brokerageItemJpaEntity.setBrokerageJpaEntity(brokerageJpaEntity);
            brokerageItemJpaEntity.setAssetJpaEntity(assetJpaEntity);
        }

        return brokerageMapper.toDomain(
                brokerageJpaRepository.save(brokerageJpaEntity)
        );
    }

    @Override
    public Brokerage loadBrokerage(UUID brokerageId) throws BrokerageNotFoundException {
        return brokerageMapper.toDomain(
                brokerageJpaRepository.findById(brokerageId).orElseThrow(() -> new BrokerageNotFoundException("Asset not found"))
        );
    }
}
