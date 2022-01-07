package com.bortoti.cleanstock.mapper;

import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.entity.OperationJpaEntity;
import com.bortoti.cleanstock.application.domain.Asset;
import com.bortoti.cleanstock.application.domain.Operation;
import com.bortoti.cleanstock.application.domain.enums.OperationType;
import com.bortoti.cleanstock.application.port.in.command.SendOperationCommand;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OperationMapperTest {
    OperationMapper operationMapper = Mappers.getMapper(OperationMapper.class);

    @Test
    void sendOperationCommandToOperation() {
        SendOperationCommand sendOperationCommand = SendOperationCommand.builder()
                .ticker("TEST3")
                .date(LocalDate.now())
                .totalValue(BigDecimal.valueOf(100.10))
                .type(OperationType.BUY)
                .quantity(BigDecimal.valueOf(10.0))
                .build();

        Operation operation = operationMapper.toOperation(sendOperationCommand);

        assertEquals(sendOperationCommand.getDate(), operation.getDate());
        assertEquals(sendOperationCommand.getQuantity(), operation.getQuantity());
        assertEquals(sendOperationCommand.getTotalValue(), operation.getTotalValue());
        assertEquals(sendOperationCommand.getType(), operation.getType());
    }

    @Test
    void operationToOperationJpaEntity() {
        Asset asset = new Asset(UUID.randomUUID(), "TEST3");

        Operation buyOperation = Operation.createBuy(asset,
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(10),
                LocalDate.now());

        Operation sellOperation = Operation.createSell(asset,
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(10),
                LocalDate.now());

        OperationJpaEntity operationJpaEntityBuy = operationMapper.operationToOperationJpaEntity(buyOperation);
        OperationJpaEntity operationJpaEntitySell = operationMapper.operationToOperationJpaEntity(sellOperation);

        assertEquals(buyOperation.getDate(), operationJpaEntityBuy.getDate());
        assertEquals(OperationType.BUY, operationJpaEntityBuy.getType());
        assertEquals(buyOperation.getQuantity(), operationJpaEntityBuy.getQuantity());
        assertEquals(buyOperation.getTotalValue(), operationJpaEntityBuy.getTotalValue());

        assertEquals(sellOperation.getDate(), operationJpaEntitySell.getDate());
        assertEquals(OperationType.SELL, operationJpaEntitySell.getType());
        assertEquals(sellOperation.getQuantity(), operationJpaEntitySell.getQuantity());
        assertEquals(sellOperation.getTotalValue(), operationJpaEntitySell.getTotalValue());

    }

    @Test
    void operationJpaEntityToOperation() {
        AssetJpaEntity assetJpaEntity = new AssetJpaEntity(UUID.randomUUID(), "TEST3");

        OperationJpaEntity operationJpaEntity = new OperationJpaEntity(
                UUID.randomUUID(),
                assetJpaEntity,
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(10),
                OperationType.BUY,
                LocalDate.now()
        );

        Operation operation = operationMapper.toOperation(operationJpaEntity);

        assertEquals(operationJpaEntity.getDate(), operation.getDate());
        assertEquals(OperationType.BUY, operation.getType());
        assertEquals(operationJpaEntity.getQuantity(), operation.getQuantity());
        assertEquals(operationJpaEntity.getTotalValue(), operation.getTotalValue());
    }
}