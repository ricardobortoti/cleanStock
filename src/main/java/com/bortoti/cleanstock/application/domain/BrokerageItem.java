package com.bortoti.cleanstock.application.domain;

import com.bortoti.cleanstock.application.domain.enums.OperationType;
import com.bortoti.cleanstock.mapper.annotation.Default;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Getter
public class BrokerageItem {
    private UUID brokerageItemId;
    /**
     * Ticker do Ativo
     */
    private String assetTicker;
    /**
     * Quantidade Comprada/Vendida
     */
    private BigDecimal quantity;
    /**
     * Preço Negociado
     */
    private BigDecimal price;
    /**
     * Compra/Venda
     */
    private OperationType type;

    @Default
    public BrokerageItem(UUID brokerageItemId,
                         String assetTicker,
                         BigDecimal quantity,
                         BigDecimal price,
                         OperationType type) {
        this.brokerageItemId = brokerageItemId;
        this.assetTicker = assetTicker;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }

    public BrokerageItem(String assetTicker,
                         BigDecimal quantity,
                         BigDecimal price,
                         OperationType type) {
        this.assetTicker = assetTicker;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }

    public BigDecimal getItemValue() {
        return quantity.multiply(price);
    }

    public BigDecimal getItemValueWithCosts(BigDecimal totalOperationsValue, BigDecimal totalOperationsCosts) {
        var itemValue = getItemValue();
        var itemCoefficient = itemValue.divide(totalOperationsValue, 10, RoundingMode.HALF_DOWN);

        return itemValue.add(totalOperationsCosts.multiply(itemCoefficient)).setScale(10);
    }
}
