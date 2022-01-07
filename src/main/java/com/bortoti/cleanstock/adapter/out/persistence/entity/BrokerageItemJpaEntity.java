package com.bortoti.cleanstock.adapter.out.persistence.entity;

import com.bortoti.cleanstock.application.domain.enums.OperationType;
import com.bortoti.cleanstock.mapper.annotation.Default;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Getter
@Setter
public class BrokerageItemJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne
    @JoinColumn(name = "brokerage_jpa_entity_brokerage_id")
    private BrokerageJpaEntity brokerageJpaEntity;

    @Default
    public BrokerageItemJpaEntity(UUID brokerageItemId, String assetTicker, BigDecimal quantity, BigDecimal price, OperationType type, BrokerageJpaEntity brokerageJpaEntity) {
        this.brokerageItemId = brokerageItemId;
        this.assetTicker = assetTicker;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.brokerageJpaEntity = brokerageJpaEntity;
    }
}