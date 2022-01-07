package com.bortoti.cleanstock.application.port.in.command;

import com.bortoti.cleanstock.application.domain.enums.OperationType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class SendOperationCommand {
    private final String ticker;
    private final BigDecimal quantity;
    private final BigDecimal totalValue;
    private final LocalDate date;
    private final OperationType type;

    public SendOperationCommand(
            String ticker,
            BigDecimal quantity,
            BigDecimal totalValue,
            LocalDate date,
            OperationType type) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.totalValue = totalValue;
        this.date = date;
        this.type = type;
    }
}
