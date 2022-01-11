package com.bortoti.cleanstock.adapter.out.persistence.entity;

import com.bortoti.cleanstock.application.domain.BrokerageItem;
import com.bortoti.cleanstock.mapper.annotation.Default;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class BrokerageJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "brokerageJpaEntity", cascade = CascadeType.ALL)
    private List<BrokerageItemJpaEntity> brokerageItems;

    @Default
    public BrokerageJpaEntity(UUID brokerageId, BigDecimal netBrokerageValue, LocalDate date) {
        BrokerageId = brokerageId;
        this.netBrokerageValue = netBrokerageValue;
        this.date = date;
    }
}
