package com.bortoti.cleanstock.adapter.out.persistence;

import com.bortoti.cleanstock.adapter.exception.AssetNotFoundException;
import com.bortoti.cleanstock.adapter.exception.OperationNotFoundException;
import com.bortoti.cleanstock.adapter.out.persistence.entity.OperationJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.repository.AssetJpaRepository;
import com.bortoti.cleanstock.adapter.out.persistence.repository.OperationJpaRepository;
import com.bortoti.cleanstock.application.domain.Operation;
import com.bortoti.cleanstock.application.port.out.SaveOperationPort;
import com.bortoti.cleanstock.mapper.OperationMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OperationPersistenceAdapter implements SaveOperationPort {
    private final OperationJpaRepository operationJpaRepository;
    private final OperationMapper operationMapper = Mappers.getMapper(OperationMapper.class);
    private final AssetJpaRepository assetJpaRepository;

    @Override
    public Operation saveOperation(Operation operation) throws AssetNotFoundException {
        OperationJpaEntity operationJpaEntity = operationMapper.operationToOperationJpaEntity(operation);

        var assetJpaEntity = assetJpaRepository.findByTicker(operation.getAsset().getTicker()).orElseThrow(AssetNotFoundException::new);
        operationJpaEntity.setAssetJpaEntity(assetJpaEntity);

         return operationMapper.toOperation(
                operationJpaRepository.save(operationJpaEntity)
        );
    }

    public Operation loadOperation(UUID id) throws OperationNotFoundException {
        return operationMapper.toOperation(operationJpaRepository.findById(id).orElseThrow(OperationNotFoundException::new));
    }
}
