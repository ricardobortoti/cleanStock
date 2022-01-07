package com.bortoti.cleanstock.adapter.out.persistence;

import com.bortoti.cleanstock.adapter.out.persistence.repository.BrokerageJpaRepository;
import com.bortoti.cleanstock.application.domain.Brokerage;
import com.bortoti.cleanstock.application.port.out.LoadBrokeragePort;
import com.bortoti.cleanstock.application.port.out.SaveBrokeragePort;
import com.bortoti.cleanstock.application.service.exception.AssetNotFoundException;
import com.bortoti.cleanstock.application.service.exception.BrokerageNotFoundException;
import com.bortoti.cleanstock.mapper.BrokerageMapper;
import com.bortoti.cleanstock.mapper.OperationMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BrokeragePersistenceAdapter implements SaveBrokeragePort, LoadBrokeragePort {
    private final BrokerageJpaRepository brokerageJpaRepository;
    private BrokerageMapper brokerageMapper = Mappers.getMapper(BrokerageMapper.class);

    @Override
    public Brokerage saveBrokerage(Brokerage brokerage) {
        var brokerageJpaEntity = brokerageMapper.toJpaEntity(brokerage);

        brokerageJpaEntity.getBrokerageItems().stream().forEach(i->i.setBrokerageJpaEntity(brokerageJpaEntity));

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
