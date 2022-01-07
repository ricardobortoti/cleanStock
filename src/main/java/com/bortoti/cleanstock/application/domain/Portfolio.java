package com.bortoti.cleanstock.application.domain;

import com.bortoti.cleanstock.application.domain.enums.OperationType;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class Portfolio {
    private Set<Operation> operations;

    public Portfolio(List<Operation> operations) {
        operations.stream()
                .sorted(Comparator.comparing(Operation::getDate))
                .collect(Collectors.toList());

        this.operations = Set.copyOf(operations);
    }


    public BigDecimal getAquisitionCost(String ticker) {
        Supplier<Stream<Operation>> buyStreamSupplier = () -> operations.stream()
                .filter(o -> o.getType() == OperationType.BUY && o.getAsset().getTicker() == ticker);

        BigDecimal sumTotalValue = buyStreamSupplier.get()
                .map(Operation::getTotalValue)
                .collect(Collectors.toList())
                .stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        if (sumTotalValue.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;

        BigDecimal sumQuantity = buyStreamSupplier.get()
                .map(Operation::getQuantity)
                .collect(Collectors.toList())
                .stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        return sumTotalValue.divide(sumQuantity);
    }
}
