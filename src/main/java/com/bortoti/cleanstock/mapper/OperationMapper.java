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
    //@Mapping(target = "assetJpaEntity", ignore = true)
    @Mapping(target = "assetJpaEntity", source = "asset")
    OperationJpaEntity operationToOperationJpaEntity(Operation operation);
    @Mapping(target = "operationId", source = "id")
    @Mapping(target = "asset", source = "assetJpaEntity")
    Operation toOperation(OperationJpaEntity operationJpaEntity);
}
