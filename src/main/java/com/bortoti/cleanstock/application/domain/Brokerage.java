package com.bortoti.cleanstock.application.domain;

import com.bortoti.cleanstock.mapper.annotation.Default;
import lombok.AccessLevel;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Getter
public class Brokerage {
    private UUID BrokerageId;
    /**
     * Valor Líquido da Nota
     */
    private BigDecimal netBrokerageValue;
    /**
     * Data da Nota
     */
    private LocalDate date;
    /**
     * Itens da Nota
     */
    @Getter(AccessLevel.NONE)
    private List<BrokerageItem> brokerageItems;

    @Default
    public Brokerage(UUID brokerageId,
                     BigDecimal netBrokerageValue,
                     LocalDate date,
                     List<BrokerageItem> brokerageItems) {
        this.BrokerageId = brokerageId;
        this.date = date;
        this.netBrokerageValue = netBrokerageValue;
        this.brokerageItems = brokerageItems;
    }

    public Brokerage(BigDecimal netBrokerageValue,
                     LocalDate date,
                     List<BrokerageItem> brokerageItems) {
        this.netBrokerageValue = netBrokerageValue;
        this.date = date;
        this.brokerageItems = brokerageItems;
    }

    public long getItemCount(){
        return brokerageItems.stream().count();
    }

    public BigDecimal getTotalOperationsValue(){
        return brokerageItems.stream()
                .map(brokerageItem -> brokerageItem.getItemValue() )
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(4);
    }

    public BigDecimal getTotalCosts() {
        return netBrokerageValue.subtract(getTotalOperationsValue()).setScale(4);
    }

    public List<BrokerageItem> getBrokerageItems() {
        return List.copyOf(brokerageItems);
    }
}
