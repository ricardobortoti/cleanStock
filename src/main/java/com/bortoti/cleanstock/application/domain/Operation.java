package com.bortoti.cleanstock.application.domain;

import com.bortoti.cleanstock.application.domain.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Operation {
    private UUID operationId;
    private Asset asset;
    private BigDecimal quantity;
    private BigDecimal totalValue;
    private OperationType type;
    private LocalDate date;

    public static Operation createBuy(Asset asset, BigDecimal quantity, BigDecimal value, LocalDate date) {
        return new Operation(UUID.randomUUID(), asset, quantity, value, OperationType.BUY, date);
    }

    public static Operation createSell(Asset asset, BigDecimal quantity, BigDecimal value, LocalDate date) {
        return new Operation(UUID.randomUUID(), asset, quantity, value, OperationType.SELL, date);
    }
}
