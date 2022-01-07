package com.bortoti.cleanstock.adapter.out.persistence;

import com.bortoti.cleanstock.adapter.out.persistence.entity.OperationJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.repository.OperationJpaRepository;
import com.bortoti.cleanstock.application.domain.Operation;
import com.bortoti.cleanstock.application.port.out.SaveOperationPort;
import com.bortoti.cleanstock.mapper.OperationMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OperationPersistenceAdapter implements SaveOperationPort {
    private final OperationJpaRepository operationJpaRepository;
    private OperationMapper operationMapper = Mappers.getMapper(OperationMapper.class);

    @Override
    public Operation saveOperation(Operation operation) {
        OperationJpaEntity operationJpaEntity = operationMapper.operationToOperationJpaEntity(operation);

        return operationMapper.toOperation(
                operationJpaRepository.save(operationJpaEntity)
        );
    }
}
