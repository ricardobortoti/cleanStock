package com.bortoti.cleanstock.mapper;

import com.bortoti.cleanstock.adapter.out.persistence.entity.OperationJpaEntity;
import com.bortoti.cleanstock.application.domain.Operation;
import com.bortoti.cleanstock.application.port.in.command.SendOperationCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OperationMapper {
    @Mapping(target = "operationId", ignore = true)
    @Mapping(target = "asset", ignore = true)
    Operation toOperation(SendOperationCommand sendOperationCommand);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assetJpaEntity", ignore = true)
    OperationJpaEntity operationToOperationJpaEntity(Operation operation);
    @Mapping(target = "operationId", ignore = true)
    @Mapping(target = "asset", ignore = true)
    Operation toOperation(OperationJpaEntity operationJpaEntity);
}
